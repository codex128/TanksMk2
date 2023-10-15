/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import codex.tanksmk2.components.*;
import codex.tanksmk2.util.GameUtils;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class EntityFactory {
    
    public static EntityId createBullet(FactoryInfo info, Vector3f spawn, Vector3f direction, BulletStats stats) {
        var bullet = info.ed.createEntity();
        info.ed.setComponents(bullet,
            new GameObject("bullet"),
            ModelInfo.create("bullet", info.ed),
            new Position(spawn),
            new Rotation(direction, Vector3f.UNIT_Y),
            new Direction(direction),
            new Speed(stats.getSpeed()),
            new Bounces(stats.getBounces()),
            new Health(1),
            new Damage(stats.getDamage(), Damage.IMPACT),
            new FaceVelocity(),
            new ApplyImpulseOnImpact(.25f),
            new RemoveOnDeath(ModelInfo.class),
            new DecayFromDeath(0),
            // for testing
            GameUtils.duration(info.time, 10)
        );
        return bullet;
    }
    
    public static EntityId createMissile(FactoryInfo info, Vector3f spawn, Vector3f direction, BulletStats stats) {
        return createBullet(info, spawn, direction, stats);
    }    
        
    public static EntityId createMuzzleflash(FactoryInfo info, Vector3f position, Rotation rotation, double time) {
        var id = info.ed.createEntity();
        info.ed.setComponents(id,
            new GameObject("muzzleflash"),
            ModelInfo.create("muzzleflash", info.ed),
            new Position(position),
            rotation,
            GameUtils.duration(info.time, time)
        );
        return id;
    }
    
    public static EntityId createExplosion(FactoryInfo info, Vector3f position, float power, float size) {
        var id = info.ed.createEntity();
        info.ed.setComponents(id,
            new Position(position),
            new Shockwave(power, size)
        );
        return id;
    }
    
}
