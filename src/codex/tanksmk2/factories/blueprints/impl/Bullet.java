/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories.blueprints.impl;

import codex.tanksmk2.components.*;
import codex.tanksmk2.factories.BulletStats;
import codex.tanksmk2.factories.blueprints.AbstractBlueprint;
import codex.tanksmk2.util.GameUtils;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class Bullet extends AbstractBlueprint {
    
    @Override
    public void create() {
        createMinimal();
        setPosition(0, 0, 0);
        setDirection(Vector3f.UNIT_Z);
        setSpeed(1);
        setDamage(1);
    }
    @Override
    public void create(EntityId customer) {
        createMinimal();
        GameUtils.getWorldTransform(ed, customer, tempTransform);
        setPosition(tempTransform.getTranslation());
        setDirection(tempTransform.getRotation().mult(Vector3f.UNIT_Z));
        BulletStats bullet;
        var channel = ed.getComponent(customer, AmmoChannel.class);
        if (channel != null) {
            bullet = switch (channel.getChannel()) {
                case Inventory.BULLETS -> BulletStats.BULLET;
                case Inventory.MISSILES -> BulletStats.MISSILE;
                default -> throw new UnsupportedOperationException("Does not support ammo channel "+channel.getChannel()+".");
            };
        } else {
            bullet = BulletStats.BULLET;
        }
        var stats = ed.getComponent(customer, Stats.class);
        if (stats != null) {
            setBulletStats(bullet.apply(stats));
        } else {
            setBulletStats(bullet);
        }
    }
    @Override
    public void setRotation(Rotation rotation) {}
    
    private void createMinimal() {
        main = ed.createEntity();
        ed.setComponents(main,
            new GameObject("bullet"),
            ModelInfo.create("bullet", ed),
            new VolumeContactShape(VolumeContactShape.SPHERE, .5f),
            new Rotation(Vector3f.UNIT_Z, Vector3f.UNIT_Y),
            new Direction(Vector3f.UNIT_Z),
            new Health(1),
            new FaceVelocity(),
            new ApplyImpulseOnImpact(.25f),
            new ApplyImpulseOnRicochet(.05f),
            new RemoveOnDeath(ModelInfo.class),
            new DecayFromDeath(0)
        );
    }
    
    public void setDirection(Vector3f direction) {
        ed.setComponents(main,
            new Rotation(direction, Vector3f.UNIT_Y),
            new Direction(direction)
        );
    }
    public void setSpeed(float speed) {
        ed.setComponent(main, new Speed(speed));
    }
    public void setBounces(int bounces) {
        ed.setComponent(main, new Bounces(bounces));
    }
    public void setDamage(float damage) {
        ed.setComponent(main, new Damage(damage, Damage.IMPACT));
    }
    public void setBulletStats(BulletStats stats) {
        ed.setComponents(main,
            new Speed(stats.getSpeed()),
            new Bounces(stats.getBounces()),
            new Damage(stats.getDamage(), Damage.IMPACT)
        );
    }
    
}
