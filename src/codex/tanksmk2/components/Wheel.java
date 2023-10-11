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
public class Wheel implements EntityComponent {
    
    private final float angle;

    public Wheel(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }
    public Wheel add(float angle) {
        return new Wheel(this.angle+angle);
    }
    @Override
    public String toString() {
        return "Wheel{" + "angle=" + angle + '}';
    }
    
}
