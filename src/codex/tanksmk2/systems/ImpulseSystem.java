/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.Force;
import codex.tanksmk2.components.TargetTo;
import com.simsilica.bullet.Impulse;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 * Converts game impulses to bullet system impulses.
 * 
 * @author codex
 */
public class ImpulseSystem extends AbstractGameSystem {
    
    private EntityData ed;
    private EntitySet entities;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        entities = ed.getEntities(Force.class, TargetTo.class);
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        entities.applyChanges();
        for (var e : entities) {
            var f = e.get(Force.class);
            var i = ed.getComponent(e.get(TargetTo.class).getTargetId(), Impulse.class);
            if (i == null) {
                i = new Impulse(f.getLinear(), f.getAngular());
            }
            else {
                i = new Impulse(i.getLinearVelocity().add(f.getLinear()), i.getAngularVelocity().add(f.getAngular()));
            }
            ed.setComponent(e.get(TargetTo.class).getTargetId(), i);
            if (f.isMomentary()) {
                ed.removeComponent(e.getId(), Force.class);
            }
        }
    }
    
}
