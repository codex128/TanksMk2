/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.input;

import codex.tanksmk2.components.AimDirection;
import codex.tanksmk2.components.TargetMove;
import codex.tanksmk2.util.GameUtils;
import com.simsilica.es.Entity;
import com.simsilica.lemur.Axis;
import com.simsilica.sim.SimTime;

/**
 * Publishes hardware input as components.
 * 
 * @author codex
 */
public interface PlayerInputPublisher {
    
    public void onEnable();
    public void onDisable();
    public default void update(SimTime time) {}
    
    public static void setTankMoveDirection(Entity entity, Axis axis, float rawValue) {
        entity.set(new TargetMove(GameUtils.insertParameter(entity.get(TargetMove.class).getDirection(), axis, GameUtils.applyDeadzone(rawValue, .1f))));        
    }
    public static void setTankAimDirection(Entity entity, Axis axis, float rawValue) {
        entity.set(new AimDirection(GameUtils.insertParameter(entity.get(AimDirection.class).getDirection(), axis, GameUtils.applyDeadzone(rawValue, .1f))));        
    }
    
}
