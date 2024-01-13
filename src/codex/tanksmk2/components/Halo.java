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
public class Halo implements EntityComponent {
    
    private final float radius, alpha;

    public Halo(float radius, float alpha) {
        this.radius = radius;
        this.alpha = alpha;
    }

    public float getRadius() {
        return radius;
    }
    public float getAlpha() {
        return alpha;
    }
    
}
