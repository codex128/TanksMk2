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
public class Touch implements EntityComponent {
    
    private final EntityId other;

    public Touch(EntityId other) {
        this.other = other;
    }

    public EntityId getOther() {
        return other;
    }
    @Override
    public String toString() {
        return "Contact{" + "other=" + other + '}';
    }
    
}
