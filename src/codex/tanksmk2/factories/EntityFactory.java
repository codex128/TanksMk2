/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import codex.tanksmk2.components.*;
import codex.tanksmk2.curves.Curve;
import codex.tanksmk2.curves.Handle;
import codex.tanksmk2.util.GameUtils;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Easing;
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
            new ApplyImpulseOnRicochet(.05f),
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
            new EntityLight(EntityLight.POINT),
            new Position(position),
            rotation,
            new Power(100f),
            new LightColor(ColorRGBA.Orange),
            GameUtils.duration(info.time, time)
        );
        return id;
    }
    
    public static EntityId createExplosion(FactoryInfo info, Vector3f position, float power, float size) {
        var id = info.ed.createEntity();
        info.ed.setComponents(id,
            new GameObject("explosion"),
            ModelInfo.create("explosion", info.ed),
            new EntityLight(EntityLight.POINT),
            new Position(position),
            new Rotation(),
            new Shockwave(power, size),
            new Power(.1f),
            new PowerCurve(info.time.getTimeInSeconds(), new Curve(
                new Handle(0f, .1f, Easing.smoothStep),
                new Handle(.05f, 1000f, Easing.smoothStep),
                new Handle(.5f, .1f))
            ),
            new LightColor(ColorRGBA.Orange),
            EmitOnce.INSTANCE,
            GameUtils.duration(info.time, 5.0)
        );
        return id;
    }
    
}
