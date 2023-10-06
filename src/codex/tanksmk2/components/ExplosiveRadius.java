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
public class ExplosiveRadius implements EntityComponent {
    
    private final float radius;

    public ExplosiveRadius(float radius) {
        this.radius = radius;
    }
    
    public float getRadius() {
        return radius;
    }
    @Override
    public String toString() {
        return "ExplosiveRadius{" + "radius=" + radius + '}';
    }
    
}
