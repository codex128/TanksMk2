/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.DeliveryTarget;
import codex.tanksmk2.components.Inventory;
import codex.tanksmk2.components.InventoryLimit;
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
        suppliers = ed.getEntities(DeliveryTarget.class, Supplier.class);
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
        var target = e.get(DeliveryTarget.class).getTarget();
        var inv = ed.getComponent(target, Inventory.class);
        var limit = ed.getComponent(target, InventoryLimit.class);
        if (inv != null) {
            int value = incrementInvValue(inv.get(s.getChannel()), s.getAmount());
            if (limit != null) {
                value = limitInvValue(value, limit.get(s.getChannel()));
            }
            var instance = new Inventory(inv);
            instance.getValues()[s.getChannel()] = value;
            ed.setComponent(target, instance);
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
