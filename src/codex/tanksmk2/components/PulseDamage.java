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
public class PulseDamage implements EntityComponent {
    
    private final float damage;
    private final double startTime;
    private final double frequency;

    public PulseDamage(float damage, double startTime, double frequency) {
        this.damage = damage;
        this.startTime = startTime;
        this.frequency = frequency;
    }

    public float getDamage() {
        return damage;
    }
    public double getStartTime() {
        return startTime;
    }
    public double getFrequency() {
        return frequency;
    }
    @Override
    public String toString() {
        return "PulseDamage{" + "damage=" + damage + ", startTime=" + startTime + ", frequency=" + frequency + '}';
    }
    
}
