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
public class BasicTurret implements EntityComponent {
    
    private final EntityId[] turrets;
    private final float speed;

    public BasicTurret(float speed, EntityId... turrets) {
        this.speed = speed;
        this.turrets = turrets;
    }

    public EntityId[] getTurrets() {
        return turrets;
    }
    public float getSpeed() {
        return speed;
    }
    
}
