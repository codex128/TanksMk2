/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import codex.tanksmk2.components.*;
import codex.tanksmk2.util.GameUtils;
import com.jme3.math.Vector3f;
import com.jme3.shader.VarType;
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
            new FaceVelocity(),
            new ApplyImpulseOnImpact(),
            GameUtils.duration(info.time, 10)
        );
        return bullet;
    }
    
    public static EntityId createMissile(FactoryInfo info, Vector3f spawn, Vector3f direction, BulletStats stats) {
        return createBullet(info, spawn, direction, stats);
    }    
    
    public static EntityId[] createLoot(FactoryInfo info, EntityId id, Loot loot) {
        var array = new EntityId[loot.getLoot().length];
        for (int i = 0; i < array.length; i++) {
            var in = loot.getLoot()[i];
            array[i] = switch (in.getPrefab().getName(info.ed)) {
                case "MatParam" -> createMatParam(info, id, in);
                default -> null;
            };
        }
        return array;
    }
    
    private static EntityId createMatParam(FactoryInfo info, EntityId id, LootInfo lootInfo) {
        var setter = info.ed.createEntity();
        info.ed.setComponents(setter,
            new MatValue(
                (String)lootInfo.getArguments()[0],
                (VarType)lootInfo.getArguments()[1],
                lootInfo.getArguments()[2]
            ),
            new TargetTo(id),
            GameUtils.duration(info.time, 0.1)
        );
        return setter;
    }
    
}
