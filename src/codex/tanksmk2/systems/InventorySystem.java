/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.StatsBuff;
import codex.tanksmk2.components.Inventory;
import codex.tanksmk2.components.Stats;
import codex.tanksmk2.components.Supplier;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class InventorySystem extends AbstractGameSystem {

    private EntityData ed;
    private EntitySet suppliers;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        suppliers = ed.getEntities(Supplier.class);
    }
    @Override
    protected void terminate() {
        suppliers.release();
    }
    @Override
    public void update(SimTime time) {
        if (suppliers.applyChanges()) {
            suppliers.getAddedEntities().forEach(e -> applyIncomingSupplies(e));
        }
    }
    
    private void applyIncomingSupplies(Entity e) {
        var s = e.get(Supplier.class);
        var inventory = ed.getComponent(s.getTarget(), Inventory.class);
        if (inventory != null) {
            int value = incrementInvValue(inventory.get(s.getChannel()), s.getAmount());
            //var limit = ed.getComponent(target, Stats.class);
            //if (limit != null) {
            //    value = limitInvValue(value, (int)limit.get(Stats.MAX_BULLETS+s.getChannel()));
            //}
            var i = new Inventory(inventory);
            i.getValues()[s.getChannel()] = value;
            ed.setComponent(s.getTarget(), i);
        }
        ed.removeComponent(e.getId(), Supplier.class);
    }
    private int incrementInvValue(int value, int n) {
        if (value < 0) return value;
        else return value+n;
    }
    private int limitInvValue(int value, int max) {
        if (max < 0 || value < 0) return value;
        else return Math.min(value, max);
    }
    
}
