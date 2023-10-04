/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.Firerate;
import codex.tanksmk2.components.Stats;
import codex.tanksmk2.components.Trigger;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class FirerateSystem extends AbstractGameSystem {
    
    private EntityData ed;
    private EntitySet entities;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        entities = ed.getEntities(Stats.class, Trigger.class, Firerate.class);
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        entities.applyChanges();
        for (var e : entities) {
            var rate = e.get(Firerate.class);
            var trigger = e.get(Trigger.class);
            if (trigger.get(Trigger.FIRERATE) != rate.isComplete(time)) {
                e.set(trigger.set(Trigger.FIRERATE, rate.isComplete(time)));
            }
        }
    }
    
}
