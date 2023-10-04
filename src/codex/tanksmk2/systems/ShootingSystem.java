/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.AmmoChannel;
import codex.tanksmk2.components.Direction;
import codex.tanksmk2.components.EquipedGuns;
import codex.tanksmk2.components.GameObject;
import codex.tanksmk2.components.ModelInfo;
import codex.tanksmk2.components.Position;
import codex.tanksmk2.components.Rotation;
import codex.tanksmk2.components.Speed;
import codex.tanksmk2.components.Stats;
import codex.tanksmk2.components.Supplier;
import codex.tanksmk2.components.Trigger;
import codex.tanksmk2.util.GameUtils;
import com.jme3.math.Vector3f;
import com.simsilica.es.CreatedBy;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
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
                shoot(e);
            }
        }
    }
    
    private void shoot(Entity e) {
        for (var g : e.get(EquipedGuns.class).getGuns()) {
            createBullet(e, g);
        }
        
    }
    private EntityId createBullet(Entity owner, EntityId gun) {
        var transform = GameUtils.getWorldTransform(ed, gun);
        var stats = owner.get(Stats.class);
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
            new Speed(stats.get(Stats.BULLET_ACCEL)),
            new CreatedBy(owner.getId())
        );
        return id;
    }
    private EntityId useInvResource(Entity entity) {
        var supplier = ed.createEntity();
        ed.setComponents(supplier,
            new Supplier(entity.getId(), entity.get(AmmoChannel.class).getChannel(), -1)
        );
        return supplier;
    }
    
}
