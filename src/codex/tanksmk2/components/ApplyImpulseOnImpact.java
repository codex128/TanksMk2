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
public class ApplyImpulseOnImpact implements EntityComponent {
    
    private final float factor;

    public ApplyImpulseOnImpact() {
        this(1f);
    }
    public ApplyImpulseOnImpact(float factor) {
        this.factor = factor;
    }

    public float getFactor() {
        return factor;
    }
    @Override
    public String toString() {
        return "ApplyImpulseOnImpact{" + "factor=" + factor + '}';
    }
    
}
