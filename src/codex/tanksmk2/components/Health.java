/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import codex.tanksmk2.util.FunctionFilter;
import com.simsilica.es.ComponentFilter;
import com.simsilica.es.EntityComponent;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class Health implements EntityComponent {
    
    public static final float MAX_POINTS = 100;
    
    private final float points;

    public Health(float points) {
        this.points = points;
    }

    public float getPoints() {
        return points;
    }
    public boolean isExhausted() {
        return points <= 0;
    }
    @Override
    public String toString() {
        return "HealthPoints{" + points + '}';
    }
    
    public Health applyDamage(float damage, Stats stats) {
        return applyDamage(damage, (stats != null ? stats.get(Stats.ARMOR) : 0));
    }
    public Health applyDamage(float damage, float armor) {
        return applyDamage(damage*(damage < 0 ? 1 : 1f-armor));
    }
    public Health applyDamage(float damage) {
        return new Health(points-damage);
    }
    
    public static ComponentFilter<Health> filter(boolean alive) {
        return new FunctionFilter<>(Health.class, c -> c.isExhausted() != alive);
    }
    
}
