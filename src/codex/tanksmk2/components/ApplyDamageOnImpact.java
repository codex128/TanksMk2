/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 * Indicates that this entity applies damage when it impacts another entity.
 * 
 * <p>Impact occurs when a bullet collides with another entity and <em>does
 * not bounce</em>.
 * 
 * @author codex
 */
public class ApplyDamageOnImpact implements EntityComponent {
    
    private final Class[] damageTypes;
    private final long duration;
    
    public ApplyDamageOnImpact(Class... damageType) {
        this(100, damageType);
    }
    public ApplyDamageOnImpact(long duration, Class... damageType) {
        this.damageTypes = damageType;
        this.duration = duration;
    }

    public Class[] getDamageType() {
        return damageTypes;
    }
    public long getDuration() {
        return duration;
    }
    @Override
    public String toString() {
        return "ApplyDamageOnImpact{" + "damageType=" + damageTypes.length + '}';
    }
    
}
