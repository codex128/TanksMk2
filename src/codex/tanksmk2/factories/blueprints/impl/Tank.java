/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories.blueprints.impl;

import codex.tanksmk2.components.*;
import codex.tanksmk2.factories.Prefab;
import codex.tanksmk2.factories.blueprints.AbstractBlueprint;
import codex.tanksmk2.util.GameUtils;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.shader.VarType;
import com.simsilica.bullet.Mass;
import com.simsilica.bullet.ShapeInfo;
import com.simsilica.bullet.SpawnPosition;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class Tank extends AbstractBlueprint {
    
    private EntityId base, treads, turret, stats, color1, color2;
    private EntityId[] guns;
    
    @Override
    public void create() {
        main = ed.createEntity();
        base = ed.createEntity();
        treads = ed.createEntity();
        turret = ed.createEntity();
        stats = ed.createEntity();
        color1 = ed.createEntity();
        color2 = ed.createEntity();
        guns = new EntityId[] {ed.createEntity()};
        ed.setComponents(main,
            new GameObject("tank"),
            ModelInfo.create("tank", ed),
            ShapeInfo.create("tank", ed),
            //new GeometricShapeInfo(Prefab.generateUnique(), GeometricShape.MergedHull),
            new Mass(2000f),
            new SpawnPosition(new Vector3f()),
            WorldTransform.INIT,
            new Position(),
            new Rotation(),
            new Stats(),
            new Inventory(-1),
            new EquipedGuns(guns),
            new InputChannels(InputChannels.SHOOT, InputChannels.SCOPE),
            new TriggerPull(false),
            new TriggerType(TriggerType.AUTO),
            new Firerate(0),
            new AmmoChannel(Inventory.BULLETS),
            new Health(100),
            new FlashHealthChange("Overlay"),
            KillBulletOnTouch.INSTANCE,
            new DecayFromDeath(.2),
            new RemoveOnDeath(ModelInfo.class, ShapeInfo.class),
            new CreateOnDeath(Prefab.create("explosion", ed))
        );
        ed.setComponents(base,
            new GameObject("tank-base"),
            new Parent(main),
            new BoneInfo(main, "base"),
            new ApplyBoneRotation(ApplyBoneRotation.ENTITY_TO_BONE),
            new Rotation(),
            new InputChannels(InputChannels.MOVE),
            new TurnSpeed(4f),
            new StatPointer(main, Stats.MOVE_SPEED),
            new Drive(true),
            new TankMoveDirection(Vector3f.ZERO),
            new Pipeline(main, TankMoveDirection.class) // copies the movement component over to the main entity
        );
        ed.setComponents(treads,
            new GameObject("tread"),
            new Parent(base),
            new Tread("TreadOffset1", 0f),
            new TargetTo(main)
        );
        ed.setComponents(turret,
            new GameObject("turret"),
            new Parent(main),
            new BoneInfo(main, "turret"),
            new ApplyBonePosition(ApplyBonePosition.BONE_TO_ENTITY),
            new ApplyBoneRotation(ApplyBoneRotation.ENTITY_TO_BONE),
            new Position(),
            new Rotation(),
            new InputChannels(InputChannels.AIM)
        );
        ed.setComponents(guns[0],
            new GameObject("gun"),
            new Parent(turret),
            new BoneInfo(main, "muzzle"),
            new ApplyBonePosition(ApplyBonePosition.BONE_TO_ENTITY),
            new Position(),
            new Rotation(),
            new CreateOnShoot(Prefab.create("muzzleflash", ed)),
            new LaserInfo(.02f, ColorRGBA.Red.mult(.5f).setAlpha(1)),
            new Bounces(0)
        );
//        ed.setComponents(pGunShot,
//            new GameObject("audio"),
//            new Parent(main),
//            AudioInfo.create("Sounds/Bang.wav", ed),
//            new AudioLoadType(AudioData.DataType.Buffer),
//            new Volume(2),
//            new Pitch(1),
//            new PositionalAudio(false, true),
//            new InfluenceCone()
//        );
        ed.setComponents(stats,
            new GameObject("basic-stats"),
            new Parent(main),
            new Stats(true)
                .set(Stats.MOVE_SPEED, 8f)
                .set(Stats.MOVE_ACCEL, 2f),
            new TargetTo(main)
        );
        ed.setComponents(color1,
            new MatValue("MainColor", VarType.Vector4, ColorRGBA.Blue),
            new TargetTo(main),
            GameUtils.duration(time, 2.0)
        );
        ed.setComponents(color2,
            new MatValue("SecondaryColor", VarType.Vector4, ColorRGBA.DarkGray),
            new TargetTo(main),
            GameUtils.duration(time, 2.0)
        );
    }
    @Override
    public void create(EntityId customer) {
        create();
        GameUtils.getWorldTransform(ed, customer, tempTransform);
        setPosition(tempTransform.getTranslation());
    }
    
    public void setPlayerId(PlayerId id) {
        ed.setComponent(main, id);
        ed.setComponent(base, id);
        ed.setComponent(turret, id);
    }
    public void setTeam(int team) {
        ed.setComponent(main, new Team(team));
    }
    public void setTriggerType(int type) {
        ed.setComponent(main, new TriggerType(type));
    }
    public void setGunLaserBounces(int bounces) {
        for (var g : guns) {
            if (bounces > 0) {
                ed.setComponent(g, LaserEmitter.INSTANCE);
            }
            ed.setComponent(g, new Bounces(bounces));
        }
    }
    public void setupHud(int alignment) {
        ed.setComponent(main, new HeadsUpDisplay("tank", alignment));
    }
    public void setInventory(int i, int n) {
        ed.getComponent(main, Inventory.class).set(i, n);
    }
    public void setStat(int i, float f) {
        ed.getComponent(stats, Stats.class).set(i, f);
    }
    public void setMainColor(ColorRGBA color) {
        ed.setComponent(color1, new MatValue("MainColor", VarType.Vector4, ColorRGBA.DarkGray));
    }
    public void setSecondaryColor(ColorRGBA color) {
        ed.setComponent(color2, new MatValue("SecondaryColor", VarType.Vector4, ColorRGBA.DarkGray));
    }
    public void setOutlineColor(ColorRGBA color) {
        if (color != null) {
            ed.setComponent(main, new Outline(color));
        } else {
            ed.removeComponent(main, Outline.class);
        }
    }
    public void setHealth(float health) {
        ed.setComponent(main, new Health(health));
    }

    public EntityId getMain() {
        return main;
    }
    public EntityId getBase() {
        return base;
    }
    public EntityId getTreads() {
        return treads;
    }
    public EntityId getTurret() {
        return turret;
    }
    public EntityId getStats() {
        return stats;
    }
    public EntityId getColor1() {
        return color1;
    }
    public EntityId getColor2() {
        return color2;
    }
    public EntityId[] getGuns() {
        return guns;
    }
    
}
