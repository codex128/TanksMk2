/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class MomentaryDamage implements EntityComponent {
    
    private final float damage;
    
    public MomentaryDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }
    @Override
    public String toString() {
        return "MomentaryDamage{" + "damage=" + damage + '}';
    }
    
}
