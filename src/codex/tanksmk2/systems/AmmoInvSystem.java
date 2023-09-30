/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.AmmoChannel;
import codex.tanksmk2.components.Inventory;
import codex.tanksmk2.components.Trigger;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class AmmoInvSystem extends AbstractGameSystem {

    private EntityData ed;
    private EntitySet entities;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        entities = ed.getEntities(Trigger.class, Inventory.class, AmmoChannel.class);
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        if (entities.applyChanges()) {
            entities.getAddedEntities().forEach(e -> update(e));
            entities.getRemovedEntities().forEach(e -> update(e));
        }
    }
    
    private void update(Entity e) {
        e.set(e.get(Trigger.class).set(Trigger.AMMO, e.get(Inventory.class).get(e.get(AmmoChannel.class).getChannel()) != 0));
    }
    
}
