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
public class Parent implements EntityComponent {
    
    private EntityId parent;

    public Parent(EntityId parent) {
        this.parent = parent;
    }

    public EntityId getId() {
        return parent;
    }
    @Override
    public String toString() {
        return "Parent{" + parent + '}';
    }
    
}
