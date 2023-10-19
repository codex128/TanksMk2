/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.AreaOfEffect;
import codex.tanksmk2.components.Damage;
import codex.tanksmk2.components.Health;
import codex.tanksmk2.components.Position;
import codex.tanksmk2.components.TargetTo;
import com.jme3.math.FastMath;
import com.simsilica.es.CreatedBy;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import com.simsilica.es.common.Decay;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 * Area Of Effect damage system.
 * 
 * <p>Applies damage entities to damagable entities within the effect range,
 * and removes them when they exit.
 * 
 * @author codex
 */
public class AOEDamageSystem extends AbstractGameSystem {

    private EntityData ed;
    private EntitySet aoe, targets, damagers;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        aoe = ed.getEntities(Position.class, AreaOfEffect.class, Damage.class);
        targets = ed.getEntities(Position.class, Health.class);
        damagers = ed.getEntities(Damage.class, TargetTo.class, CreatedBy.class);
    }
    @Override
    protected void terminate() {
        aoe.release();
        targets.release();
        damagers.release();
    }
    @Override
    public void update(SimTime time) {
        if (aoe.applyChanges() | targets.applyChanges()) {
            damagers.applyChanges();
            for (var a : aoe) for (var t : targets) {
                var damager = getDamagerBetween(a.getId(), t.getId());
                boolean inside = isWithinEffect(a, t);
                if (damager == null && inside) {
                    ed.setComponents(ed.createEntity(), a.get(Damage.class), new TargetTo(t.getId()), new CreatedBy(a.getId()));
                }
                else if (damager != null && !inside) {
                    ed.setComponent(damager.getId(), new Decay(time.getTime()));
                }
            }
            // remove damagers of removed AOEs
            for (var a : aoe.getRemovedEntities()) for (var d : damagers) {
                if (d.get(CreatedBy.class).getCreatorId().equals(a.getId())) {
                    ed.setComponent(d.getId(), new Decay(time.getTime()));
                }
            }
        }
    }
    
    private Entity getDamagerBetween(EntityId aoe, EntityId target) {
        for (var d : damagers) {
            if (d.get(CreatedBy.class).getCreatorId().equals(aoe)
                    && d.get(TargetTo.class).getTargetId().equals(target)) {
                return d;
            }
        }
        return null;
    }
    private boolean isWithinEffect(Entity aoe, Entity target) {
        return target.get(Position.class).getPosition().distanceSquared(aoe.get(Position.class).getPosition()) < FastMath.sqr(aoe.get(AreaOfEffect.class).getRadius());
    }
    
}
