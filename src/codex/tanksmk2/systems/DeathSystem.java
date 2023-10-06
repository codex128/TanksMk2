/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.Dead;
import codex.tanksmk2.components.DecayFromDeath;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.es.common.Decay;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class DeathSystem extends AbstractGameSystem {
    
    private EntityData ed;
    private EntitySet decay;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        decay = ed.getEntities(DecayFromDeath.class, Dead.class);
    }
    @Override
    protected void terminate() {
        decay.release();
    }
    @Override
    public void update(SimTime time) {
        if (decay.applyChanges()) {
            decay.getAddedEntities().forEach(e -> generateDecay(e, time));
        }
    }
    
    private void generateDecay(Entity e, SimTime time) {
        var dec = e.get(DecayFromDeath.class);
        if (dec.isForceDecay() || ed.getComponent(e.getId(), Decay.class) == null) {
            ed.setComponent(e.getId(), dec.generateDecay(time));
        }
    }
    
}
