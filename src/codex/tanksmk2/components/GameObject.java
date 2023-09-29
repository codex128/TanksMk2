/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 * Names the entity.
 * 
 * <p>It should not matter what an entity is named, this component
 * is just here for detecting if an entity exists at all, especially
 * for children. In general, all entities should have this component.
 * 
 * @author codex
 */
public class GameObject implements EntityComponent {
    
    private final String name;

    public GameObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "GameObject{" + "name=" + name + '}';
    }
    
}
