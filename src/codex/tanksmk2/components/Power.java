/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.jme3.math.Vector2f;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class Power implements EntityComponent {
    
    private final float power;

    public Power(float power) {
        this.power = power;
    }
    public Power(double power) {
        this.power = (float)power;
    }

    public float getPower() {
        return power;
    }
    @Override
    public String toString() {
        return "Power{" + power + '}';
    }
    
}
