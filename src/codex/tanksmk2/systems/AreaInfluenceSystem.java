/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.AreaOfInfluence;
import codex.tanksmk2.components.Damage;
import codex.tanksmk2.components.Health;
import codex.tanksmk2.components.Stats;
import codex.tanksmk2.components.TargetTo;
import codex.tanksmk2.components.WorldTransform;
import com.jme3.math.FastMath;
import com.simsilica.es.CreatedBy;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;
import java.util.LinkedList;

/**
 * Area Of Effect system.
 * <p>
 * Applies influence entities (i.e. damage) to target entities within
 * the influence range, and removes them when they exit the influence range.
 * 
 * @author codex
 */
public class AreaInfluenceSystem extends AbstractGameSystem {

    private EntityData ed;
    private final LinkedList<InfluenceTypeManager> managers = new LinkedList<>();
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        managers.add(new InfluenceTypeManager(
            ed.getEntities(WorldTransform.class, AreaOfInfluence.class, Damage.class),
            ed.getEntities(Damage.class, TargetTo.class, CreatedBy.class),
            ed.getEntities(WorldTransform.class, Health.class),
            Damage.class
        ));
        managers.add(new InfluenceTypeManager(
            ed.getEntities(WorldTransform.class, AreaOfInfluence.class, Stats.class),
            ed.getEntities(Stats.class, TargetTo.class, CreatedBy.class),
            ed.getEntities(WorldTransform.class, Stats.class),
            Stats.class
        ));
    }
    @Override
    protected void terminate() {
        for (var m : managers) {
            m.release();
        }
    }
    @Override
    public void update(SimTime time) {
        for (var i : managers) {
            i.update(time);
        }
    }
      
    private Entity getMediatorBetween(EntitySet mediators, EntityId aoe, EntityId target) {
        for (var d : mediators) if (d.get(CreatedBy.class).getCreatorId().equals(aoe)
                && d.get(TargetTo.class).getTargetId().equals(target)) {
            return d;
        }
        return null;
    }
    private boolean isWithinEffect(Entity aoe, Entity target) {
        var t1 = aoe.get(WorldTransform.class);
        var t2 = target.get(WorldTransform.class);
        return t1.getTranslation().distanceSquared(t2.getTranslation()) < FastMath.sqr(aoe.get(AreaOfInfluence.class).getRadius());
    }
    
    private class InfluenceTypeManager {
        
        public EntitySet influencers;
        public EntitySet mediators;
        public EntitySet targets;
        public Class<? extends EntityComponent> primary;
        
        public InfluenceTypeManager(EntitySet influencers, EntitySet mediators, EntitySet targets, Class<? extends EntityComponent> primary) {
            this.influencers = influencers;
            this.mediators = mediators;
            this.targets = targets;
            this.primary = primary;
        }
        
        public void update(SimTime time) {
            if (influencers.applyChanges() | targets.applyChanges()) {
                mediators.applyChanges();
                for (var a : influencers.getRemovedEntities()) for (var d : mediators) {
                    if (d.get(CreatedBy.class).getCreatorId().equals(a.getId())) {
                        ed.removeEntity(d.getId());
                    }
                }
                for (var a : influencers) for (var t : targets) {
                    if (ed.getComponent(t.getId(), AreaOfInfluence.class) != null) {
                        continue;
                    }
                    Entity mid = getMediatorBetween(mediators, a.getId(), t.getId());
                    boolean inside = isWithinEffect(a, t);
                    if (mid == null && inside) {
                        ed.setComponents(ed.createEntity(), a.get(primary), new TargetTo(t.getId()), new CreatedBy(a.getId()));
                    } else if (mid != null && !inside) {
                        ed.removeEntity(mid.getId());
                    }
                }
            }
        }
        public void release() {
            influencers.release();
            mediators.release();
            targets.release();
        }
        
    }
    
}
