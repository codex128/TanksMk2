/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class HitPoints implements EntityComponent {
    
    public static final float MAX_HITPOINTS = 100;
    
    private final float points;

    public HitPoints(float points) {
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
        return "HitPoints{" + points + '}';
    }
    
    public HitPoints applyDamage(float damage, Stats stats, SimTime time) {
        return applyDamage(damage, (stats != null ? stats.get(Stats.ARMOR) : 0));
    }
    public HitPoints applyDamage(float damage, float armor) {
        return applyDamage(damage*(1f-armor));
    }
    public HitPoints applyDamage(float damage) {
        return new HitPoints(points-damage);
    }
    
}
