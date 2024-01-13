/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import codex.tanksmk2.util.FunctionFilter;
import com.jme3.math.FastMath;
import com.simsilica.es.ComponentFilter;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class Health implements EntityComponent {
    
    private final float health;
    private final float max;
    private final float gain;

    public Health(float health) {
        this.max = this.health = Math.max(health, 0);
        this.gain = 0;
    }
    public Health(float health, float max) {
        this(health, max, 0);
    }
    public Health(float health, float max, float gain) {
        this.max = Math.max(max, 0);
        this.health = FastMath.clamp(health, 0, this.max);
        this.gain = gain;
    }

    public float getHealth() {
        return health;
    }
    public float getMaxHealth() {
        return max;
    }
    public float getHealthPercent() {
        return health/max;
    }
    public float getHealthGain() {
        return gain;
    }
    public float getPercentGain() {
        return gain/max;
    }
    public boolean isExhausted() {
        return health <= 0;
    }
    
    @Override
    public String toString() {
        return "Health{" + health + '/' + max + '}';
    }
    
    public Health applyDamage(float damage, Stats stats) {
        return applyDamage(damage, (stats != null ? stats.get(Stats.ARMOR) : 0));
    }
    public Health applyDamage(float damage, float armor) {
        return applyDamage(damage*(damage < 0 ? 1 : 1f-armor));
    }
    public Health applyDamage(float damage) {
        return new Health(health-damage, max, -damage);
    }
    
    public static ComponentFilter<Health> filter(boolean alive) {
        return new FunctionFilter<>(Health.class, c -> c.isExhausted() != alive);
    }
    
}
