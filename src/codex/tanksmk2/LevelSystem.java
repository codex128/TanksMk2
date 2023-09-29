/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2;

import codex.j3map.J3map;
import codex.tanksmk2.components.*;
import codex.tanksmk2.systems.CameraState;
import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.simsilica.bullet.ShapeInfo;
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
        var pTurret = ed.createEntity();
        var pGun = ed.createEntity();
        var pBasicStats = ed.createEntity();
        //var source = (J3map)assetManager.loadAsset("Properties/tank.stats");
        ed.setComponents(player,
            new GameObject("player"),
            scene,
            pId,
            ModelInfo.create("tank", ed),
            ShapeInfo.create("tank", ed),
            new EntityTransform(),
            new Stats(),
            new Inventory(),
            new EquipedGuns(pGun)
            //new InputChannel(InputChannel.SHOOT)
        );
        ed.setComponents(pBase,
            new GameObject("tank-base"),
            new Parent(player),
            pId, // note: player id is required for recieving inputs
            new BoneInfo(player, "base"),
            new EntityTransform(),
            new InputChannel(InputChannel.MOVE),
            new TurnSpeed(0.01f),
            new Drive(true),
            new Pipeline(player, TankMoveDirection.class) // copies the velocity component over to the main player entity
        );
        ed.setComponents(pTurret,
            new GameObject("tank-turret"),
            new Parent(player),
            pId,
            new BoneInfo(player, "turret"),
            new EntityTransform(),
            new InputChannel(InputChannel.AIM)
        );
        ed.setComponents(pGun,
            new GameObject("tank-gun"),
            new Parent(pTurret),
            pId,
            //new BoneInfo(player, "gun"),
            new EntityTransform()
        );
        ed.setComponents(pBasicStats,
            new GameObject("base-stats-buff"),
            new Parent(player),
            new Stats(),
            new StatsBuff(player)
        );
        
        var camera = ed.createEntity();
        ed.setComponents(camera,
            new GameObject("player-camera"),
            CameraState.APP_CAMERA,
            new EntityTransform(
                new Vector3f(-10, 10, -10),
                new Quaternion().lookAt(new Vector3f(1, -1, 1), Vector3f.UNIT_Y)),
            new CameraPriority()
        );
    
    }
    @Override
    protected void terminate() {}
    
}
