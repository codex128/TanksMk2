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
public class EquipedGuns implements EntityComponent {
    
    private final EntityId[] guns;

    public EquipedGuns(EntityId... guns) {
        this.guns = guns;
    }

    public EntityId[] getGuns() {
        return guns;
    }
    @Override
    public String toString() {
        return "EquipedGuns{" + "guns=" + guns + '}';
    }
    
}
