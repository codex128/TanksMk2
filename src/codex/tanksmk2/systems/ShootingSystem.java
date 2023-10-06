/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.*;
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

/**
 *
 * @author codex
 */
public class ShootingSystem extends AbstractGameSystem {
    
    private EntityData ed;
    private EntitySet entities;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        entities = ed.getEntities(EquipedGuns.class, Trigger.class, Stats.class, AmmoChannel.class);
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        entities.applyChanges();
        for (var e : entities) {
            if (e.get(Trigger.class).allFlagsSatisfied()) {
                shoot(e, time);
            }
        }
    }
    
    private void shoot(Entity e, SimTime time) {
        for (var g : e.get(EquipedGuns.class).getGuns()) {
            createBullet(e, g, time);
        }
        useResources(e);
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
    private void useResources(Entity e) {
        ed.setComponent(ed.createEntity(), new Supplier(e.get(AmmoChannel.class).getChannel(), -1));
    }
    
}
