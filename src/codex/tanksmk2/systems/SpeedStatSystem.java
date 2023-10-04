/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.Speed;
import codex.tanksmk2.components.Stats;
import codex.tanksmk2.util.EntityMaintainer;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class SpeedStatSystem extends AbstractGameSystem {

    private EntityData ed;
    private SpeedUpdate entities;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        entities = new SpeedUpdate(ed);
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        entities.update(time);
    }
    
    private class SpeedUpdate extends EntityMaintainer {

        public SpeedUpdate(EntityData ed) {
            super(ed, Speed.class, Stats.class);
        }
        
        @Override
        public void update(Entity e, SimTime time) {
            e.set(new Speed(e.get(Stats.class).get(Stats.MOVE_SPEED)));
        }
        
    }
    
}
