/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.Stats;
import codex.tanksmk2.components.TankMoveDirection;
import codex.tanksmk2.components.Velocity;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class TankMovementSystem extends AbstractGameSystem {

    private EntityData ed;
    private EntitySet entities;
    
    @Override
    protected void initialize() {
        System.out.println("initialize tank movement system");
        ed = getManager().get(EntityData.class);
        entities = ed.getEntities(TankMoveDirection.class, Stats.class);
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        if (entities.applyChanges()) {
            entities.getAddedEntities().forEach(e -> update(e));
            entities.getChangedEntities().forEach(e -> update(e));
        }
    }
    
    private void update(Entity e) {
        ed.setComponent(e.getId(), new Velocity(e.get(TankMoveDirection.class).getDirection(), e.get(Stats.class).get(Stats.MOVE_SPEED)));
    }
    
}
