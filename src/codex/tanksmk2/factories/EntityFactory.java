/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import codex.tanksmk2.components.*;
import com.jme3.math.Vector3f;
import com.jme3.shader.VarType;
import com.simsilica.bullet.Impulse;
import com.simsilica.bullet.ShapeInfo;
import com.simsilica.bullet.SpawnPosition;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.common.Decay;
import com.simsilica.sim.GameSystemManager;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class EntityFactory {
    
    private final EntityData ed;
    private final GameSystemManager manager;
    
    public EntityFactory(EntityData ed, GameSystemManager manager) {
        this.ed = ed;
        this.manager = manager;
    }
    
    public EntityId createBullet(Vector3f spawn, Vector3f direction, float speed) {
        var bullet = ed.createEntity();
        ed.setComponents(bullet,
                new GameObject("bullet"),
                ModelInfo.create("bullet", ed),
                new Position(spawn),
                new Rotation(),
                new Direction(direction),
                new Speed(speed));
        return bullet;
    }
    public EntityId createMissile(Vector3f spawn, Vector3f direction, float speed) {
        var missile = ed.createEntity();
        ed.setComponents(missile,
                new GameObject("bullet"),
                ModelInfo.create("missile", ed),
                new Position(spawn),
                new Rotation(),
                new Direction(direction),
                new Speed(speed));
        return missile;
    }
    public EntityId createGrenade(Vector3f spawn, Vector3f direction, float speed, SimTime time, float delay) {
        var grenade = ed.createEntity();
        ed.setComponents(grenade,
                new GameObject("grenade"),
                ModelInfo.create("grenade", ed),
                ShapeInfo.create("grenade", ed),
                new SpawnPosition(spawn),
                new Impulse(direction.mult(speed)),
                new Decay(time.getTime(), time.getFutureTime(delay)));
        return grenade;
    }
    
    public EntityId[] createLoot(EntityId id, Loot loot) {
        var array = new EntityId[loot.getLoot().length];
        for (int i = 0; i < array.length; i++) {
            var info = loot.getLoot()[i];
            array[i] = switch (info.getPrefab().getName(ed)) {
                case "mat-param" -> createMatParam(id, info);
                default -> null;
            };
        }
        return array;
    }
    private EntityId createMatParam(EntityId id, LootInfo info) {
        var setter = ed.createEntity();
        ed.setComponents(setter,
            new MatValue(
                (String)info.getArguments()[0],
                (VarType)info.getArguments()[1],
                info.getArguments()[2]
            ),
            new TargetTo(id),
            Decay.duration(manager.getStepTime().getTime(), manager.getStepTime().toSimTime(0.1))
        );
        return setter;
    }
    
}
