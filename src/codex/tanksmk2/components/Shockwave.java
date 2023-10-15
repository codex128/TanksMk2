/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class Shockwave implements EntityComponent {
    
    private final float power;
    private final float falloff;

    public Shockwave(float power, float falloff) {
        this.power = power;
        this.falloff = falloff;
    }

    /**
     * Physical force at distance zero from the shockwave.
     * 
     * @return force
     */
    public float getPower() {
        return power;
    }
    
    /**
     * Distance where force becomes zero.
     * 
     * @return falloff
     */
    public float getFalloff() {
        return falloff;
    }
    
    @Override
    public String toString() {
        return "Shockwave{" + "power=" + power + ", falloff=" + falloff + '}';
    }
    
}
