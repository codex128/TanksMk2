/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2;

import codex.tanksmk2.bullet.GeometricShape;
import codex.tanksmk2.components.*;
import codex.tanksmk2.factories.Prefab;
import codex.tanksmk2.states.CameraState;
import codex.tanksmk2.factories.blueprints.impl.Tank;
import codex.vfx.utils.VfxUtils;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.simsilica.bullet.Mass;
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
        
        ed = getManager().get(EntityData.class);
        assetManager = getManager().get(AssetManager.class);
        scene = SceneId.create();
        
        Tank player = new Tank();
        player.setCreationInfo(ed, getManager().getStepTime());
        player.create();
        player.setPosition(new Vector3f(-7, 0, -7));
        player.setPlayerId(PlayerId.create());
        player.setTeam(0);
        player.setStat(Stats.BOUNCES, 1);
        player.setStat(Stats.MOVE_SPEED, 12);
        //player.setGunLaserBounces(1);
        player.setupHud(HeadsUpDisplay.BOTTOM_RIGHT);
        player.setInventory(Inventory.BULLETS, 900);
        //player.setOutlineColor(ColorRGBA.White.mult(.1f));
        player.setOutlineColor(ColorRGBA.Blue);
        player.setHealth(200);
        
        Tank enemy = new Tank();
        enemy.setCreationInfo(ed, getManager().getStepTime());
        for (int a = -1; a < 2; a += 2) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // creates a new set of entities to work with
                    enemy.create();
                    enemy.setPosition(new Vector3f(12*a+(i*3*a), 0, 22-j*3));
                    enemy.setTeam(1);
                    enemy.setTriggerType(TriggerType.SEMI);
                    enemy.setMainColor(ColorRGBA.Green);
                    enemy.setOutlineColor(ColorRGBA.Red.mult(.2f));
                    enemy.setStat(Stats.BOUNCES, 3);
                    enemy.setStat(Stats.BULLET_SPEED, 30);
                    enemy.setStat(Stats.DAMAGE, 25);
                    //enemy.setGunLaserBounces(0);
                    ed.setComponent(enemy.getMain(), new BasicTurret(1f, enemy.getTurret()));
                    ed.setComponent(enemy.getTurret(), new Rotation(VfxUtils.gen.nextFloat(FastMath.TWO_PI), Vector3f.UNIT_Y));
                }
            }
        }
        
        var camera = ed.createEntity();
        ed.setComponents(camera,
            new GameObject("player-camera"),
            CameraState.APP_CAMERA,
            new Position(0, 45, -50),
            new Rotation(new Vector3f(0, -1, 1), Vector3f.UNIT_Y),
            new AudioListener(0),
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
            new Position(0, -5, 0),
            new Rotation()
            //GameUtils.duration(getManager().getStepTime(), 10)
        );
        
        for (int i = 0; i < 10; i++) {
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
        
        var damageInfluencer = ed.createEntity();
        ed.setComponents(damageInfluencer,
            new GameObject("AOE"),
            new Parent(level),
            ModelInfo.create("cube", ed),
            WorldTransform.INIT,
            new Position(-10, 0, 10),
            new AreaOfInfluence(10f),
            new Damage(2f, Damage.PULSE)
        );
        
        var statInfluencer = ed.createEntity();
        ed.setComponents(statInfluencer,
            new GameObject("AOE"),
            new Parent(level),
            ModelInfo.create("cube", ed),
            WorldTransform.INIT,
            new Position(-10, 0, -10),
            new AreaOfInfluence(5f),
            new Stats()
                .set(Stats.FIRERATE, -0.05f)
                .set(Stats.BULLET_SPEED, 20)
                .set(Stats.BOUNCES, 3)
                //.set(Stats.MOVE_SPEED, 10)
                //.set(Stats.MOVE_ACCEL, 10)
        );
    
    }
    @Override
    protected void terminate() {}
    
}
