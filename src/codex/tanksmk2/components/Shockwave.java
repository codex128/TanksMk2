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

    public Shockwave(float power) {
        this.power = power;
    }

    public float getPower() {
        return power;
    }
    @Override
    public String toString() {
        return "Shockwave{" + "power=" + power + '}';
    }
    
}
