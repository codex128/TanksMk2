/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.CreateOnDeath;
import codex.tanksmk2.components.DecayFromDeath;
import codex.tanksmk2.components.Health;
import codex.tanksmk2.components.RemoveOnDeath;
import codex.tanksmk2.factories.CustomerEntityFactory;
import codex.tanksmk2.factories.FactoryInfo;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
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
    private EntitySet create, remove, decay;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        create = ed.getEntities(Health.filter(false), CreateOnDeath.class, Health.class);
        remove = ed.getEntities(Health.filter(false), RemoveOnDeath.class, Health.class);
        decay = ed.getEntities(Health.filter(false), DecayFromDeath.class, Health.class);
    }
    @Override
    protected void terminate() {
        decay.release();
    }
    @Override
    public void update(SimTime time) {
        if (create.applyChanges()) {
            create.forEach(e -> create(e, time));
        }
        if (remove.applyChanges()) {
            remove.forEach(e -> removeTaggedComponents(e));
        }
        if (decay.applyChanges()) {
            decay.forEach(e -> generateDecay(e, time));
        }
    }
    
    private void create(Entity e, SimTime time) {
        CustomerEntityFactory.create(new FactoryInfo(ed, time), e.get(CreateOnDeath.class), e.getId(), false);
        ed.removeComponent(e.getId(), CreateOnDeath.class);
    }
    private void removeTaggedComponents(Entity e) {
        for (var c : e.get(RemoveOnDeath.class).getComponents()) {
            ed.removeComponent(e.getId(), c);
        }
        ed.removeComponent(e.getId(), RemoveOnDeath.class);
    }
    private void generateDecay(Entity e, SimTime time) {
        var dec = e.get(DecayFromDeath.class);
        if (dec.isForceDecay() || ed.getComponent(e.getId(), Decay.class) == null) {
            ed.setComponent(e.getId(), dec.generateDecay(time));
        }
        // consume component
        ed.removeComponent(e.getId(), DecayFromDeath.class);
    }
    
}
