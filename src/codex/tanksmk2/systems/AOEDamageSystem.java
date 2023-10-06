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
            Entity effecter;
            for (var a : aoe) for (var t : targets) {
                effecter = null;
                // Fetch the active damager that links with aoe with this target.
                // This is essentially self-reloading, since as soon as the previous damager
                // dies, this is able to detect that and create a new one if necessary.
                for (var d : damagers) {
                    if (d.get(CreatedBy.class).getCreatorId().equals(a.getId()) && d.get(TargetTo.class).getTargetId().equals(t.getId())) {
                        effecter = d;
                        break;
                    }
                }
                boolean inside = t.get(Position.class).getPosition().distanceSquared(a.get(Position.class).getPosition()) < FastMath.sqr(a.get(AreaOfEffect.class).getRadius());
                if (effecter == null && inside) {
                    ed.setComponents(ed.createEntity(), a.get(Damage.class), new TargetTo(t.getId()), new CreatedBy(a.getId()));
                }
                else if (effecter != null && !inside) {
                    ed.setComponent(effecter.getId(), new Decay(time.getTime()));
                }
            }
        }
    }
    
}
