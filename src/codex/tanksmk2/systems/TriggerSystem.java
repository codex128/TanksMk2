/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.AmmoChannel;
import codex.tanksmk2.components.Firerate;
import codex.tanksmk2.components.Inventory;
import codex.tanksmk2.components.Trigger;
import codex.tanksmk2.components.TriggerInput;
import codex.tanksmk2.util.EntityMaintainer;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;
import java.util.LinkedList;

/**
 *
 * @author codex
 */
public class TriggerSystem extends AbstractGameSystem {

    private EntityData ed;
    private final LinkedList<EntityMaintainer> updates = new LinkedList<>();
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        updates.add(new FirerateUpdate(ed));
        updates.add(new AmmoUpdate(ed));
        updates.add(new InputUpdate(ed));
    }
    @Override
    protected void terminate() {
        updates.forEach(e -> e.release());
        updates.clear();
    }
    @Override
    public void update(SimTime time) {
        updates.forEach(e -> e.update(time));
    }
    
    private class FirerateUpdate extends EntityMaintainer {

        public FirerateUpdate(EntityData ed) {
            super(ed, Trigger.class, Firerate.class);
        }
        
        @Override
        public void update(Entity e, SimTime time) {
            e.set(e.get(Trigger.class).set(Trigger.FIRERATE, e.get(Firerate.class).isComplete(time)));
        }
        
    }
    private class AmmoUpdate extends EntityMaintainer {

        public AmmoUpdate(EntityData ed) {
            super(ed, Trigger.class, Inventory.class, AmmoChannel.class);
        }
        
        @Override
        public void update(Entity e, SimTime time) {
            e.set(e.get(Trigger.class).set(Trigger.AMMO, e.get(Inventory.class).isExhausted(e.get(AmmoChannel.class).getChannel())));
        }
        
    }
    private class InputUpdate extends EntityMaintainer {

        public InputUpdate(EntityData ed) {
            super(ed, Trigger.class, TriggerInput.class);
        }
        
        @Override
        public void update(Entity e, SimTime time) {
            e.set(e.get(Trigger.class).set(Trigger.INPUT, e.get(TriggerInput.class).isPulled()));
        }
        
    }
    
}
