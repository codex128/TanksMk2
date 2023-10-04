/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.MomentaryDamage;
import codex.tanksmk2.components.HitPoints;
import codex.tanksmk2.components.PulseDamage;
import codex.tanksmk2.components.Stats;
import codex.tanksmk2.components.TargetTo;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;
import java.util.LinkedList;

/**
 *
 * @author codex
 */
public class DamageSystem extends AbstractGameSystem {
    
    private EntityData ed;
    private LinkedList<DamageUpdate> entities = new LinkedList<>();
    private double lastFrameSeconds = 0;

    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        entities.add(new MomentaryUpdate(ed));
        entities.add(new PulseUpdate(ed));
    }
    @Override
    protected void terminate() {
        entities.forEach(e -> e.release());
        entities.clear();
    }
    @Override
    public void update(SimTime time) {
        for (DamageUpdate e : entities) {
            e.update(time);
        }
        lastFrameSeconds = time.getTimeInSeconds();
    }
    
    private abstract class DamageUpdate {
        
        private EntitySet entities;
        
        public DamageUpdate(EntityData ed, Class... components) {
            entities = ed.getEntities(components);
        }
        
        public void release() {
            entities.release();
        }
        public void update(SimTime time) {
            entities.applyChanges();
            for (var e : entities) {
                var target = e.get(TargetTo.class).getTarget();
                var hitpoints = ed.getComponent(target, HitPoints.class);
                if (hitpoints == null) {
                    remove(e);
                    continue;
                }
                update(e, target, hitpoints, time);
            }
        }
        
        protected abstract void update(Entity entity, EntityId target, HitPoints hitpoints, SimTime time);
        protected abstract void remove(Entity e);
        
        protected Stats getStats(EntityId id) {
            return ed.getComponent(id, Stats.class);
        }
        
    }
    private class MomentaryUpdate extends DamageUpdate {

        public MomentaryUpdate(EntityData ed) {
            super(ed, TargetTo.class, MomentaryDamage.class);
        }

        @Override
        protected void update(Entity entity, EntityId target, HitPoints hitpoints, SimTime time) {
            var damage = entity.get(MomentaryDamage.class);
            ed.setComponent(target, hitpoints.applyDamage(damage.getDamage(), getStats(target), time));
            remove(entity);
        }
        @Override
        protected void remove(Entity e) {
            ed.removeComponent(e.getId(), MomentaryDamage.class);
        }
        
    }
    private class PulseUpdate extends DamageUpdate {

        public PulseUpdate(EntityData ed) {
            super(ed, TargetTo.class, PulseDamage.class);
        }

        @Override
        protected void update(Entity entity, EntityId target, HitPoints hitpoints, SimTime time) {
            var damage = entity.get(PulseDamage.class);
            // If the last frame was below the pulse point, and the current
            // frame is above the pulse point, then apply damage. This is the equivalent of:
            // (currentFrameDelta % frequency) < (lastFrameDelta % frequency)
            if ((time.getTimeInSeconds()-damage.getStartTime())%damage.getFrequency() < (lastFrameSeconds-damage.getStartTime())%damage.getFrequency()) {
                ed.setComponent(target, hitpoints.applyDamage(damage.getDamage(), getStats(target), time));
            }
        }
        @Override
        protected void remove(Entity e) {}
        
    }
    
}
