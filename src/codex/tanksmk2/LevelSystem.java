/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2;

import codex.j3map.J3map;
import codex.tanksmk2.bullet.GeometricShape;
import codex.tanksmk2.components.*;
import codex.tanksmk2.factories.Prefab;
import codex.tanksmk2.states.CameraState;
import codex.tanksmk2.util.GameUtils;
import codex.tanksmk2.util.debug.ObserveTransform;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.shader.VarType;
import com.simsilica.bullet.Mass;
import com.simsilica.bullet.ShapeInfo;
import com.simsilica.bullet.SpawnPosition;
import com.simsilica.es.EntityData;
import com.simsilica.sim.AbstractGameSystem;

/**
 *
 * @author codex
 */
public class LevelSystem extends AbstractGameSystem {
    
    private EntityData ed;
    private AssetManager assetManager;
    private SceneId scene;
    
    public LevelSystem() {}
    
    @Override
    protected void initialize() {
    
        System.out.println("initialize level");
        
        ed = getManager().get(EntityData.class);
        assetManager = getManager().get(AssetManager.class);
        scene = SceneId.create();
        
        // create the player
        var player = ed.createEntity();
        var pId = PlayerId.create();
        var pBase = ed.createEntity();
        var pWheelFR = ed.createEntity();
        var pTreads = ed.createEntity();
        var pTurret = ed.createEntity();
        var pGun = ed.createEntity();
        var pBasicStats = ed.createEntity();
        var source = (J3map)assetManager.loadAsset("Properties/tank.stats");
        ed.setComponents(player,
            new GameObject("tank"),
            scene,
            pId,
            ModelInfo.create("tank", ed),
            ShapeInfo.create("tank", ed),
            //new GeometricShapeInfo(Prefab.generateUnique(), GeometricShape.MergedHull),
            new Mass(2000f),
            new SpawnPosition(new Vector3f()),
            new Position(),
            new Rotation(),
            new Stats(),
            new Inventory(-1),
            new EquipedGuns(pGun),
            new InputChannel(InputChannel.SHOOT),
            new Firerate(0),
            new AmmoChannel(Inventory.BULLETS),
            new Health(100),
            KillBulletOnTouch.INSTANCE,
            new DecayFromDeath(.2),
            new RemoveOnDeath(ModelInfo.class, ShapeInfo.class),
            new CreateOnDeath(Prefab.create("explosion", ed))
        );
        ed.setComponents(pBase,
            new GameObject("tank-base"),
            new Parent(player),
            pId,
            new BoneInfo(player, "base"),
            new ApplyBoneRotation(ApplyBoneRotation.ENTITY_TO_BONE),
            new Rotation(),
            new InputChannel(InputChannel.MOVE),
            new TurnSpeed(2.5f),
            new StatPointer(player, Stats.MOVE_SPEED),
            new Drive(true),
            new Pipeline(player, TankMoveDirection.class) // copies the velocity component over to the main player entity
        );
        ed.setComponents(pWheelFR,
            new GameObject("wheel"),
            new Parent(pBase),
            new BoneInfo(player, "wheel.FR"),
            //new ApplyBoneRotation(ApplyBoneRotation.ENTITY_TO_BONE),
            new Rotation(),
            new Wheel(0f)
        );
        ed.setComponents(pTreads,
            new GameObject("tread"),
            new Parent(pBase),
            new Tread("TreadOffset1", 0f),
            new TargetTo(player)
        );
        ed.setComponents(pTurret,
            new GameObject("turret"),
            new Parent(player),
            pId,
            new BoneInfo(player, "turret"),
            new ApplyBonePosition(ApplyBonePosition.BONE_TO_ENTITY),
            new ApplyBoneRotation(ApplyBoneRotation.ENTITY_TO_BONE),
            new Position(),
            new Rotation(),
            new InputChannel(InputChannel.AIM)
        );
        ed.setComponents(pGun,
            new GameObject("gun"),
            new Parent(pTurret),
            new BoneInfo(player, "muzzle"),
            new ApplyBonePosition(ApplyBonePosition.BONE_TO_ENTITY),
            new Position(),
            new Rotation(),
            new CreateOnShoot(Prefab.create("muzzleflash", ed))
        );
        ed.setComponents(pBasicStats,
            new GameObject("basic-stats"),
            new Parent(player),
            new Stats().set(Stats.MOVE_SPEED, 20f),
            new StatsBuff(player)
        );
        ed.setComponents(ed.createEntity(),
            new MatValue("MainColor", VarType.Vector4, ColorRGBA.Black),
            new TargetTo(player),
            GameUtils.duration(getManager().getStepTime(), 0.5)
        );
        ed.setComponents(ed.createEntity(),
            new MatValue("SecondaryColor", VarType.Vector4, ColorRGBA.DarkGray),
            new TargetTo(player),
            GameUtils.duration(getManager().getStepTime(), 0.5)
        );
        
        var camera = ed.createEntity();
        ed.setComponents(camera,
            new GameObject("player-camera"),
            CameraState.APP_CAMERA,
            new Position(0, 45, -50),
            new Rotation(new Vector3f(0, -1, 1), Vector3f.UNIT_Y),
            new CameraPriority()
        );
        
//        var floor = ed.createEntity();
//        ed.setComponents(floor,
//            new GameObject("physics-floor"),
//            ShapeInfo.create("floor", ed),
//            new Mass(0f),
//            new SpawnPosition(new Vector3f(0f, -1f, 0f))
//        );
        
        var level = ed.createEntity();
        ed.setComponents(level,
            new GameObject("level-scene"),
            new Scene(),
            new ModelInfo(Prefab.create("testLevel", ed), false),
            new Position(0f, -5f, 0f),
            new Rotation()
            //GameUtils.duration(getManager().getStepTime(), 10)
        );
        
        for (int i = 0; i < 5; i++) {
            ed.setComponents(ed.createEntity(),
                new GameObject("cube"),
                ModelInfo.create("cube", ed),
                new GeometricShapeInfo(Prefab.generateUnique(), GeometricShape.Box),
                new SpawnPosition(
                    FastMath.rand.nextFloat(-20, 20),
                    FastMath.rand.nextFloat(7, 15),
                    FastMath.rand.nextFloat(-20, 20)
                ),
                new Position(),
                new Rotation(),
                new Mass(1f),
                new ReflectOnTouch()
            );
        }
        
        var aoe = ed.createEntity();
        ed.setComponents(aoe,
            new GameObject("aoe damager"),
            new Parent(level),
            ModelInfo.create("cube", ed),
            new Position(-10, 0, 10),
            new AreaOfEffect(5f),
            new Damage(10f, Damage.PULSE)
        );
    
    }
    @Override
    protected void terminate() {}
    
}
