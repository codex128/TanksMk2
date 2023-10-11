/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.Power;
import codex.tanksmk2.components.EntityCurve;
import codex.tanksmk2.util.Curve;
import codex.tanksmk2.util.GameUtils;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityComponent;
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
    private final LinkedList<CurveUpdater> updaters = new LinkedList<>();
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        updaters.add(new CurveUpdater(ed, Power.class) {
            @Override
            public EntityComponent updateComponent(float value) {
                return new Power(value);
            }
        });
    }
    @Override
    protected void terminate() {}
    
    private abstract class CurveUpdater {
        
        private final EntitySet entities;
        
        public CurveUpdater(EntityData ed, Class<? extends EntityComponent> type) {
            entities = ed.getEntities(EntityCurve.class, type);
        }
        
        public void update(SimTime time) {
            entities.applyChanges();
            for (var e : entities) {
                update(e, time, e.get(EntityCurve.class));
            }
        }
        public void release() {
            entities.release();
        }
        
        private void update(Entity e, SimTime time, EntityCurve curve) {
            e.set(updateComponent(curve.interpolate((float)GameUtils.getSecondsSince(time, curve.getStartTime()))));
        }        
        public abstract EntityComponent updateComponent(float value);
        
    }
    
}
