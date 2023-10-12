/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import codex.tanksmk2.bullet.GeometricShape;
import codex.tanksmk2.components.*;
import codex.tanksmk2.util.GameUtils;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.shader.VarType;
import com.simsilica.bullet.Mass;
import com.simsilica.bullet.SpawnPosition;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.common.Decay;
import com.simsilica.sim.GameSystemManager;

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
    
    public EntityId createBullet(Vector3f spawn, Vector3f direction, BulletStats stats) {
        System.out.println("bullet-spawn-direction="+direction);
        var bullet = ed.createEntity();
        ed.setComponents(bullet,
            new GameObject("bullet"),
            ModelInfo.create("bullet", ed),
            new Position(spawn),
            new Rotation(direction, Vector3f.UNIT_Y),
            new Direction(direction),
            new Speed(stats.getSpeed()),
            new Bounces(stats.getBounces()),
            new FaceVelocity()
        );
        return bullet;
    }
    public EntityId createMissile(Vector3f spawn, Vector3f direction, BulletStats stats) {
        return createBullet(spawn, direction, stats);
    }
    
    public EntityId[] createLoot(EntityId id, Loot loot) {
        var array = new EntityId[loot.getLoot().length];
        for (int i = 0; i < array.length; i++) {
            var info = loot.getLoot()[i];
            array[i] = switch (info.getPrefab().getName(ed)) {
                case "MatParam" -> createMatParam(id, info);
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
    
    public EntityId createFromSpatial(String name, Spatial spatial) {
        return switch (name) {
            case "wall" -> createWallFromSpatial(spatial);
            default -> null;
        };
    }
    private EntityId createWallFromSpatial(Spatial spatial) {
        var wall = ed.createEntity();
        ed.setComponents(wall,
            new GameObject("wall"),
            new GeometricShapeInfo(Prefab.generateUnique(), GeometricShape.Mesh),
            new SpawnPosition(spatial.getLocalTranslation(), spatial.getLocalRotation()),
            new Mass(0f),
            new ReflectOnTouch()
        );
        return wall;
    }
    
}
