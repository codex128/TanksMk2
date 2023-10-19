/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.Power;
import codex.tanksmk2.components.PowerCurve;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;
import java.util.LinkedList;

/**
 *
 * @author codex
 */
public class CurveInterpolationSystem extends AbstractGameSystem {
    
    private EntityData ed;
    private final LinkedList<InterpolationUpdate> updaters = new LinkedList<>();
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        updaters.add(new InterpolationUpdate(ed, PowerCurve.class, Power.class) {
            @Override
            protected void update(Entity e, SimTime time) {
                var c = e.get(PowerCurve.class);
                e.set(new Power(c.getInterpolator().interpolate(c.getStartTime(), time)));
            }
        });
    }
    @Override
    protected void terminate() {
        for (var u : updaters) {
            u.release();
        }
    }
    @Override
    public void update(SimTime time) {
        for (var u : updaters) {
            u.update(time);
        }
    }
    
    private abstract class InterpolationUpdate {
        
        private EntitySet entities;
        
        public InterpolationUpdate(EntityData ed, Class... components) {
            entities = ed.getEntities(components);
        }
        
        public void update(SimTime time) {
            entities.applyChanges();
            for (var e : entities) {
                update(e, time);
            }
        }
        public void release() {
            entities.release();
        }
        
        protected abstract void update(Entity e, SimTime time);
        
    }
    
}
