/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.ESAppState;
import codex.tanksmk2.components.InputChannel;
import codex.tanksmk2.components.PlayerId;
import codex.tanksmk2.input.*;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityContainer;
import com.simsilica.es.EntityData;
import codex.tanksmk2.input.PlayerInputPublisher;
import codex.tanksmk2.input.TankInputFunctions;
import com.jme3.app.Application;
import java.util.HashMap;

/**
 * Handles controller inputs and converts them to tank inputs.
 * 
 * @author codex
 */
public class PlayerInputSystem extends ESAppState {
    
    public static final int MOVE_INPUT = 0;
    
    private InputPublisherContainer publishers;
    private final HashMap<PlayerId, TankInputFunctions> functions = new HashMap<>();
    
    @Override
    protected void init(Application app) {
        
        publishers = new InputPublisherContainer(ed);
        publishers.start();
        
        // Create basic keyboard functions for the first player to join.
        // Note: "first player to join" is not necessarily player 1.
        var f = TankInputFunctions.forKeyboard();
        f.initialize(inputMapper);
        f.initializeDefaultMappings(inputMapper);
        functions.put(new PlayerId(0), f);
        
    }
    @Override
    protected void cleanup(Application app) {
        publishers.stop();
    }
    @Override
    protected void onEnable() {}
    @Override
    protected void onDisable() {}
    @Override
    public void update(float tpf) {
        publishers.update();
        publishers.update(tpf);
    }
    
    private class InputPublisherContainer extends EntityContainer<PlayerInputPublisher> {

        public InputPublisherContainer(EntityData ed) {
            super(ed, PlayerId.class, InputChannel.class);
        }
        
        public void update(float tpf) {            
            for (var p : getArray()) {
                p.update(tpf);
            }
        }
        @Override
        protected PlayerInputPublisher addObject(Entity entity) {
            var publisher = createInputPublisher(entity);
            publisher.onEnable();
            enableInputGroup(publisher.getFunctions().getGroupName(), true);
            return publisher;
        }
        @Override
        protected void updateObject(PlayerInputPublisher t, Entity entity) {
            //t.update(frameTime);
        }
        @Override
        protected void removeObject(PlayerInputPublisher t, Entity entity) {
            t.onDisable();
            enableInputGroup(t.getFunctions().getGroupName(), false);
        }
        
        private PlayerInputPublisher createInputPublisher(Entity entity) {
            var funcs = functions.get(entity.get(PlayerId.class));
            switch (entity.get(InputChannel.class).getChannel()) {
                case InputChannel.MOVE -> {
                    return new MoveInputPublisher(ed, inputMapper, entity, funcs);
                }
                case InputChannel.AIM -> {
                    if (funcs.getDevice() == null) {
                        return new MouseDirectionPublisher(ed, inputManager, inputMapper, entity, funcs, cam);
                    }
                    else {
                        return new AimDirectionPublisher(ed, inputMapper, entity, funcs);
                    }
                }
                default -> throw new NullPointerException("Unknown input channel!");
            }
        }
        private void enableInputGroup(String group, boolean enable) {
            if (enable) {
                inputMapper.activateGroup(group);
            }
            else {
                boolean disable = true;
                for (var t : getArray()) if (t.getFunctions().getGroupName().equals(group)) {
                    disable = false;
                    break;
                }
                if (disable) {
                    inputMapper.deactivateGroup(group);
                }
            }
        }
        
    }
    
}
