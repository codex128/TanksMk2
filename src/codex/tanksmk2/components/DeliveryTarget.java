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
public class DeliveryTarget implements EntityComponent {
    
    private final EntityId target;

    public DeliveryTarget(EntityId target) {
        this.target = target;
    }

    public EntityId getTarget() {
        return target;
    }
    @Override
    public String toString() {
        return "DeliveryTarget{" + "target=" + target + '}';
    }
    
}
