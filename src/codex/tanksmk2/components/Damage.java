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
     * Frequency of the {@link #PULSE} damage type.
     */
    public static final double PULSE_FREQUENCY = 0.5;
    
    /**
     * Applies full damage instantly and only once.
     */
    public static final String IMPACT = "impact";
    /**
     * Applies constant damage per second.
     */
    public static final String DRAIN = "drain";
    /**
     * Applies full damage every so often as defined
     * by {@link #PULSE_FREQUENCY}.
     */
    public static final String PULSE = "pulse";
    /**
     * Reduces hit points to zero.
     */
    public static final String INFINITE = "infinite";
    
    private final float damage;
    private final String type;

    public Damage(float damage) {
        this(damage, IMPACT);
    }
    public Damage(float damage, String type) {
        this.damage = damage;
        this.type = type;
    }

    public float getDamage() {
        return damage;
    }
    public String getType() {
        return type;
    }
    @Override
    public String toString() {
        return "Damage{" + "damage=" + damage + ", type=" + type + '}';
    }
    
}
