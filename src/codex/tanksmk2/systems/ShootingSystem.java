/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.*;
import codex.tanksmk2.util.EntityMaintainer;
import codex.tanksmk2.util.GameUtils;
import com.jme3.math.Vector3f;
import com.simsilica.es.CreatedBy;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import com.simsilica.es.common.Decay;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;
import java.util.LinkedList;

/**
 *
 * @author codex
 */
public class ShootingSystem extends AbstractGameSystem {
    
    private EntityData ed;
    private EntitySet shooters;
    private final LinkedList<EntityMaintainer> updates = new LinkedList<>();
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        shooters = ed.getEntities(EquipedGuns.class, Trigger.class, Stats.class, AmmoChannel.class, Firerate.class);
        updates.add(new FirerateUpdate(ed));
        updates.add(new AmmoUpdate(ed));
        updates.add(new InputUpdate(ed));
    }
    @Override
    protected void terminate() {
        shooters.release();
        updates.forEach(e -> e.release());
        updates.clear();
    }
    @Override
    public void update(SimTime time) {
        updates.forEach(e -> e.update(time));
        shooters.applyChanges();
        for (var e : shooters) {
            if (e.get(Trigger.class).allFlagsSatisfied()) {
                shoot(e, time);
            }
        }
    }
    
    private void shoot(Entity e, SimTime time) {
        for (var g : e.get(EquipedGuns.class).getGuns()) {
            createBullet(e, g, time);
        }
        ed.setComponent(ed.createEntity(), new Supplier(e.get(AmmoChannel.class).getChannel(), -1));
        e.set(new Trigger(false));
        e.set(e.get(Firerate.class).shoot(time));
    }
    private EntityId createBullet(Entity owner, EntityId gun, SimTime time) {
        var transform = GameUtils.getWorldTransform(ed, gun);
        var id = ed.createEntity();
        ed.setComponents(id,
            new GameObject("bullet"),
            ModelInfo.create("bullet", ed),
            //ShapeInfo.create("bullet", ed),
            //new Ghost(Ghost.COLLIDE_STATIC),
            //new SpawnPosition(transform.getTranslation()),
            new Position(transform.getTranslation()),
            new Rotation(transform.getRotation()),
            new Direction(transform.getRotation().mult(Vector3f.UNIT_Z)),
            new Speed(1f),
            new CreatedBy(owner.getId()),
            new FaceVelocity(),
            new Bounces(0),
            new Decay(time.getTime(), time.getFutureTime(.5))
        );
        return id;
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
            e.set(e.get(Trigger.class).set(Trigger.AMMO, !e.get(Inventory.class).isExhausted(e.get(AmmoChannel.class).getChannel())));
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
