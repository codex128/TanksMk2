/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.Inventory;
import codex.tanksmk2.components.Supplier;
import codex.tanksmk2.components.TargetTo;
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
        System.out.println("initialize inventory system");
        ed = getManager().get(EntityData.class);
        suppliers = ed.getEntities(TargetTo.class, Supplier.class);
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
        var target = e.get(TargetTo.class).getTargetId();
        var inventory = ed.getComponent(target, Inventory.class);
        if (inventory != null) {
            var i = new Inventory(inventory);
            switch (s.getMethod()) {
                case Supplier.ADD:
                    i.getValues()[s.getChannel()] = incrementInvValue(inventory.get(s.getChannel()), s.getAmount());
                    break;
                case Supplier.SET:
                    i.getValues()[s.getChannel()] = s.getAmount();
                    break;
                default:
                    throw new IllegalArgumentException("Supply method \""+s.getMethod()+"\" does not exist!");
            }
            ed.setComponent(target, i);
        }
        // This quashes easy ways to gradually replenish
        // resources, but is generally ok for what we need.
        ed.removeComponent(e.getId(), Supplier.class);
    }
    private int incrementInvValue(int value, int n) {
        if (value < 0) return value;
        else return Math.max(value+n, 0);
    }
    
}
