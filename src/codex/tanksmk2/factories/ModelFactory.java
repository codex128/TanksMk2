/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import codex.tanksmk2.components.FlashType;
import codex.tanksmk2.effects.ColorDistanceInfluencer;
import codex.tanksmk2.states.ModelViewState;
import codex.tanksmk2.util.GameUtils;
import com.epagagames.particles.Emitter;
import com.epagagames.particles.emittershapes.EmitterSphere;
import com.epagagames.particles.influencers.GravityInfluencer;
import com.epagagames.particles.influencers.RotationLifetimeInfluencer;
import com.epagagames.particles.influencers.SizeInfluencer;
import com.epagagames.particles.influencers.SpriteInfluencer;
import com.epagagames.particles.valuetypes.Gradient;
import com.epagagames.particles.valuetypes.ValueType;
import com.epagagames.particles.valuetypes.VectorValueType;
import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class ModelFactory implements Factory<Spatial> {

    private final EntityData ed;
    private final AssetManager assetManager;

    public ModelFactory(EntityData ed, AssetManager assetManager) {
        this.ed = ed;
        this.assetManager = assetManager;
    }
    
    @Override
    public Spatial load(FactoryInfo info, EntityId customer) {
        return switch (info.name) {
            case ModelViewState.CACHE -> null;
            case "testLevel"    -> createTestLevel();
            case "cube"         -> createCube();
            case "tank"         -> createTank();
            case "bullet"       -> createBullet();
            case "muzzleflash"  -> createMuzzleflash(customer);
            case "explosion"    -> createExplosion();
            default -> null;
        };
    }
    
    public Spatial createTestLevel() {
        return assetManager.loadModel("Scenes/levels/testLevel.j3o");
    }
    
    public Spatial createCube() {
        var geometry = new Geometry("cube", new Box(1, 1, 1));
        var mat = new Material(assetManager, "Common/MatDefs/Light/PBRLighting.j3md");
        mat.setColor("BaseColor", ColorRGBA.White);
        geometry.setMaterial(mat);
        return geometry;
    }
    
    public Spatial createTank() {
        var tank = assetManager.loadModel("Models/tank/tank.j3o");
        var mat = new Material(assetManager, "MatDefs/tank.j3md");
        mat.setTexture("DiffuseMap", assetManager.loadTexture(new TextureKey("Textures/tankDiffuse.png", false)));
        tank.setMaterial(mat);
        return tank;
    }
    
    public Spatial createBullet() {
        var bullet = assetManager.loadModel("Models/bullet/bullet.j3o");
        bullet.setLocalScale(.2f);
        return bullet;
    }
    
    public Spatial createMuzzleflash(EntityId customer) {
        var type = GameUtils.getComponent(ed, customer, FlashType.class, FlashType.DEFAULT);
        return switch (type.getType()) {
            case FlashType.FLAME    -> createFlameFlash();
            default                 -> createFlameFlash();
        };
    }
    private Spatial createFlameFlash() {
        var flash = assetManager.loadModel("Models/muzzleflash/muzzleflash.j3o");
        var mat = assetManager.loadMaterial("Materials/muzzleflash.j3m");
        //flash.setLocalScale(.1f);
        mat.setTransparent(true);
        flash.setMaterial(mat);
        flash.setQueueBucket(RenderQueue.Bucket.Transparent);
        return flash;
    }
    
    public Emitter createExplosion() {
        var mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mat.setTransparent(true);
        mat.setTexture("Texture", assetManager.loadTexture("Effects/flame-burst.png"));
        var flame = new Emitter("tank-flame", mat, 100);
        //smoke.setShape(new PointEmissionShape());
        flame.setShape(new EmitterSphere(.01f));
        flame.setQueueBucket(RenderQueue.Bucket.Transparent);
        flame.setStartSpeed(new ValueType(0f, 4f));
        flame.setStartSize(new ValueType(2f));
        //flame.setStartColor(new ColorValueType(new Gradient().addGradPoint(ColorRGBA.Orange, 0f).addGradPoint(ColorRGBA.DarkGray, 1f)));
        //flame.setLifeFixedDuration(10.0f);
        flame.setEmissionsPerSecond(0);
        flame.setParticlesPerEmission(40);
        final ValueType life = new ValueType(.7f);
        flame.setLifeMinMax(life, life);
        flame.setParticlesFollowEmitter(false);
        //var coloring = new ColorInfluencer();
        //var color = ed.getComponent(id, ColorScheme.class).getPallete()[0];
        //coloring.setStartEndColor(color, color.clone().setAlpha(0f));
        //flame.addInfluencer(coloring);
        var dist = new ColorDistanceInfluencer(new Vector2f(.1f, .5f), new Gradient()
                //.addGradPoint(new ColorRGBA(1f, 1f, 1f, 1f), 0f)
                .addGradPoint(new ColorRGBA(1f, .1f, 0f, 1f), 0f)
                .addGradPoint(new ColorRGBA(.01f, .01f, .01f, 1f), 1f));
        flame.addInfluencer(dist);
        var sizing = new SizeInfluencer();
        sizing.setSizeOverTime(new ValueType(-1f));
        flame.addInfluencer(sizing);
        var gravity = new GravityInfluencer();
        gravity.setGravity(0f, 30f, 0f);
        //flame.addInfluencer(gravity);
        var rotation = new RotationLifetimeInfluencer();
        rotation.setSpeedOverLifetime(new VectorValueType(new Vector3f(2f, 2f, 2f), new Vector3f(-2f, -2f, -2f)));
        flame.addInfluencer(rotation);
        var sprite = new SpriteInfluencer();
        flame.addInfluencer(sprite);
        sprite.setSpriteRows(2);
        sprite.setSpriteCols(2);
        sprite.setAnimate(false);
        sprite.setUseRandomImage(true);
        //debris.emitAllParticles();
        return flame;
    }
    
}
