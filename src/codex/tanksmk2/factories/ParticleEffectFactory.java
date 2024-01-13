/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import codex.vfx.mesh.Quad;
import codex.vfx.particles.ParticleData;
import codex.vfx.particles.ParticleGroup;
import codex.vfx.particles.drivers.emission.ParticleFactory;
import codex.vfx.particles.geometry.InstancedParticleGeometry;
import codex.vfx.particles.tweens.Ease;
import codex.vfx.particles.tweens.Interpolator;
import codex.vfx.particles.tweens.Range;
import codex.vfx.utils.VfxUtils;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;

/**
 *
 * @author codex
 */
public class ParticleEffectFactory implements LegacyFactory<ParticleGroup> {
    
    private final AssetManager assetManager;

    public ParticleEffectFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    
    @Override
    public ParticleGroup load(FactoryInfo info) {
        return switch (info.name) {
            case "shockwave" -> createShockwave(info);
            default -> null;
        };
    }
    
    public ParticleGroup createShockwave(FactoryInfo info) {
        var group = new ParticleGroup<ParticleData>(1);
        group.setDynamicSizingStep(0);
        group.addDriver(new ParticleFactory<ParticleData>() {
            @Override
            public void particleAdded(ParticleGroup<ParticleData> group, ParticleData p) {
                p.setLife(0.4f);
                p.size = new Range(20.0f, 0.0f, Interpolator.Float, Ease.inLinear);
                p.color = new Range(ColorRGBA.BlackNoAlpha, ColorRGBA.DarkGray, Interpolator.Color, Ease.inCubic);
                p.getRotation().fromAngleAxis(VfxUtils.gen.nextFloat(FastMath.TWO_PI), Vector3f.UNIT_Y);
            }
        });
        var quad = new Quad(Vector3f.UNIT_Y, Vector3f.UNIT_Z, 2, 2, 0.5f, 0.5f);
        var geometry = new InstancedParticleGeometry(group, quad);
        geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        var mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setBoolean("UseInstancing", true);
        mat.setBoolean("VertexColor", true);
        mat.setTexture("ColorMap", assetManager.loadTexture("Effects/shockwave.png"));
        mat.setFloat("AlphaDiscardThreshold", 0.05f);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mat.setTransparent(true);
        geometry.setMaterial(mat);
        group.attachChild(geometry);
        return group;
    }
    
}
