/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.ControllerHardwareIndex;
import codex.tanksmk2.input.GamepadPublisher;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityContainer;
import com.simsilica.es.EntityData;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;
import codex.tanksmk2.input.PlayerInputPublisher;
import com.jme3.input.InputManager;
import com.simsilica.lemur.GuiGlobals;

/**
 * Handles controller inputs and converts them to tank inputs.
 * 
 * @author codex
 */
public class PlayerInputSystem extends AbstractGameSystem {
    
    private EntityData ed;
    private InputPublisherContainer adapters;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        adapters = new InputPublisherContainer(ed);
        adapters.start();
    }
    @Override
    protected void terminate() {
        adapters.stop();
    }
    @Override
    public void update(SimTime time) {
        adapters.update();
    }
    
    private class InputPublisherContainer extends EntityContainer<PlayerInputPublisher> {

        public InputPublisherContainer(EntityData ed) {
            super(ed, ControllerHardwareIndex.class);
        }
        
        @Override
        protected PlayerInputPublisher addObject(Entity entity) {
            var im = GuiGlobals.getInstance().getInputMapper();
            var index = entity.get(ControllerHardwareIndex.class);
            var publisher = switch (index.getType()) {
                case Keyboard -> new KeyboardPublisher(getManager().get(InputManager.class), im, entity);
                case Gamepad -> new GamepadPublisher(im, entity);
            };
            publisher.onEnable();
            return publisher;
        }
        @Override
        protected void updateObject(PlayerInputPublisher t, Entity entity) {
            
        }
        @Override
        protected void removeObject(PlayerInputPublisher t, Entity entity) {
            t.onDisable();
        }
        
    }
    
}
