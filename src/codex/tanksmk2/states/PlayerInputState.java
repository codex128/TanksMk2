/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.states;

import codex.tanksmk2.ESAppState;
import codex.tanksmk2.components.Health;
import codex.tanksmk2.components.InputChannels;
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
public class PlayerInputState extends ESAppState {
    
    public static final int MOVE_INPUT = 0;
    
    private InputPublisherContainer publishers;
    private final HashMap<PlayerId, TankInputFunctions> functions = new HashMap<>();
    
    @Override
    protected void init(Application app) {
        
        System.out.println("initialize player input state");
        
        var f = TankInputFunctions.forKeyboard();
        f.initialize(inputMapper);
        f.initializeDefaultMappings(inputMapper);
        functions.put(new PlayerId(0), f);
        
        publishers = new InputPublisherContainer(ed);
        publishers.start();
        
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
    
    private class InputPublisherContainer extends EntityContainer<PlayerInputPublisher[]> {

        public InputPublisherContainer(EntityData ed) {
            super(ed, PlayerId.class, InputChannels.class);
        }
        
        public void update(float tpf) {            
            for (var array : getArray()) for (var p : array) {
                p.update(tpf);
            }
        }
        @Override
        protected PlayerInputPublisher[] addObject(Entity entity) {
            var pubs = createInputPublisher(entity);
            for (var p : pubs) {
                p.onEnable();
                enableInputGroup(p.getFunctions().getGroupName(), true);
            }
            return pubs;
        }
        @Override
        protected void updateObject(PlayerInputPublisher[] array, Entity entity) {
            //t.update(frameTime);
        }
        @Override
        protected void removeObject(PlayerInputPublisher[] array, Entity entity) {
            for (var t : array) {
                t.onDisable();
                enableInputGroup(t.getFunctions().getGroupName(), false);
            }
        }
        
        private PlayerInputPublisher[] createInputPublisher(Entity entity) {
            var funcs = functions.get(entity.get(PlayerId.class));
            if (funcs == null) {
                throw new NullPointerException("Functions not found!");
            }
            var channels = entity.get(InputChannels.class);
            PlayerInputPublisher[] pubs = new PlayerInputPublisher[channels.getChannels().length];
            for (int i = 0; i < pubs.length; i++) {
                switch (channels.getChannels()[i]) {
                    case InputChannels.MOVE -> {
                        pubs[i] = new MoveInputPublisher(ed, inputMapper, entity, funcs);
                    }
                    case InputChannels.AIM -> {
                        if (funcs.getDevice() == null) {
                            pubs[i] = new MouseDirectionPublisher(ed, inputManager, entity, funcs, cam);
                        } else {
                            pubs[i] = new AimDirectionPublisher(ed, inputMapper, entity, funcs);
                        }
                    }
                    case InputChannels.SHOOT -> {
                        pubs[i] = new TriggerInputPublisher(ed, entity, inputMapper, funcs);
                    }
                    case InputChannels.SCOPE -> {
                        pubs[i] = new ScopeInputPublisher(ed, inputMapper, entity, funcs);
                    }
                    default -> {
                        throw new NullPointerException("Unknown input channel: "+channels.getChannels()[i]);
                    }
                }
            }
            return pubs;
        }
        private void enableInputGroup(String group, boolean enable) {
            if (enable) {
                inputMapper.activateGroup(group);
            } else {
                boolean disable = true;
                for (var array : getArray()) for (var t : array) {
                    if (t.getFunctions().getGroupName().equals(group)) {
                        disable = false;
                        break;
                    }
                }
                if (disable) {
                    inputMapper.deactivateGroup(group);
                }
            }
        }
        
    }
    
}
