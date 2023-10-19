/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.effects;

import com.epagagames.particles.influencers.ParticleInfluencer;
import com.epagagames.particles.particle.ParticleData;
import com.epagagames.particles.valuetypes.Gradient;
import com.jme3.math.Vector2f;

/**
 *
 * @author codex
 */
public class ColorDistanceInfluencer extends ParticleInfluencer {
    
    private Vector2f range;
    private Gradient gradient;
    
    public ColorDistanceInfluencer(Vector2f range, Gradient gradient) {
        this.range = range;
        this.gradient = gradient;
    }
    
    @Override
    public void update(ParticleData p, float tpf) {
        gradient.getValueColor(calculateDistanceValue(p.position.distance(p.emitter.getWorldTranslation())), p.color);
        p.color.a *= 1f-p.percentLife;
    }
    @Override
    public void initialize(ParticleData p) {
        
    }
    @Override
    public void reset(ParticleData p) {
        
    }
    
    private float calculateDistanceValue(float distance) {
        if (distance <= range.x) return 0f;
        else if (distance >= range.y) return 1f;
        else return (distance-range.x)/(range.y-range.x);
    }
    
}
