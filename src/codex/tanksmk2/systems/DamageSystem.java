/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.Damage;
import codex.tanksmk2.components.Health;
import codex.tanksmk2.components.Stats;
import codex.tanksmk2.components.TargetTo;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class DamageSystem extends AbstractGameSystem {
    
    private EntityData ed;
    private EntitySet entities;
    private double lastFrameSeconds = 0;

    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        entities = ed.getEntities(Damage.class, TargetTo.class);
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        entities.applyChanges();
        for (var e : entities) {
            update(e, time);
        }
        lastFrameSeconds = time.getTimeInSeconds();
    }
    
    private void update(Entity e, SimTime time) {
        var target = e.get(TargetTo.class).getTargetId();
        var hitpoints = ed.getComponent(target, Health.class);
        if (hitpoints == null) {
            consume(e);
            return;
        }
        switch (e.get(Damage.class).getType()) {
            case Damage.IMPACT   -> updateImpact(e, target, hitpoints);
            case Damage.DRAIN    -> updateDrain(e, target, hitpoints, time);
            case Damage.PULSE    -> updatePulse(e, target, hitpoints, time);
            case Damage.INFINITE -> updateInfinite(e, target);
        }
    }
    private void updateImpact(Entity e, EntityId target, Health hitpoints) {
        ed.setComponent(target, hitpoints.applyDamage(e.get(Damage.class).getDamage(), getStats(target)));
        consume(e);
    }
    private void updateDrain(Entity e, EntityId target, Health hitpoints, SimTime time) {
        ed.setComponent(target, hitpoints.applyDamage(e.get(Damage.class).getDamage()*(float)time.getTpf(), getStats(target)));
    }
    private void updatePulse(Entity e, EntityId target, Health hitpoints, SimTime time) {
        if (time.getTimeInSeconds()%Damage.PULSE_FREQUENCY < lastFrameSeconds%Damage.PULSE_FREQUENCY) {
            System.out.println("apply pulse: "+hitpoints.getPoints());
            ed.setComponent(target, hitpoints.applyDamage(e.get(Damage.class).getDamage(), getStats(target)));
        }
    }
    private void updateInfinite(Entity e, EntityId target) {
        ed.setComponent(target, new Health(0));
        consume(e);
    }
    
    private Stats getStats(EntityId id) {
        return ed.getComponent(id, Stats.class);
    }
    private void consume(Entity e) {
        ed.removeComponent(e.getId(), Damage.class);
    }
    
}
