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
public class Damage implements EntityComponent {
    
    /**
     * Frequency of damage type {@link #PULSE} in seconds.
     */
    public static final double PULSE_FREQUENCY = 0.5;
    
    /**
     * Applies full damage instantly and only once.
     */
    public static final int IMPACT = 0;
    
    /**
     * Applies constant damage per second.
     */
    public static final int DRAIN = 1;
    
    /**
     * Applies full damage at intervals of length {@link #PULSE_FREQUENCY} in seconds.
     */
    public static final int PULSE = 2;
    
    /**
     * Instantly reduces health to zero.
     */
    public static final int INFINITE = 3;
    
    private final float damage;
    private final int type;

    public Damage(float damage) {
        this(damage, IMPACT);
    }
    public Damage(int type) {
        this(0f, type);
    }
    public Damage(float damage, int type) {
        this.damage = damage;
        this.type = type;
    }

    public float getDamage() {
        return damage;
    }
    public int getType() {
        return type;
    }
    @Override
    public String toString() {
        return "Damage{" + "damage=" + damage + ", type=" + type + '}';
    }
    
}
