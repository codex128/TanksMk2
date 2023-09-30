/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.BulletShootEvent;
import codex.tanksmk2.components.EquipedGuns;
import codex.tanksmk2.components.GameObject;
import codex.tanksmk2.components.ModelInfo;
import codex.tanksmk2.components.Stats;
import codex.tanksmk2.components.Trigger;
import codex.tanksmk2.components.Velocity;
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
        entities = ed.getEntities(EquipedGuns.class, Trigger.class, Stats.class);
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
            createEvent(e, g, createBullet(e, g), time);
        }
    }
    private EntityId createBullet(Entity owner, EntityId gun) {
        var transform = GameUtils.getWorldTransform(ed, gun);
        var stats = owner.get(Stats.class);
        var id = ed.createEntity();
        ed.setComponents(id,
            new GameObject("bullet"),
            ModelInfo.create("bullet", ed),
            new Velocity(transform.getRotation().mult(Vector3f.UNIT_Z), stats.get(Stats.BULLET_ACCEL)),
            new CreatedBy(owner.getId())
        );
        return id;
    }
    private EntityId createEvent(Entity owner, EntityId gun, EntityId bullet, SimTime time) {
        
    }
    
}
