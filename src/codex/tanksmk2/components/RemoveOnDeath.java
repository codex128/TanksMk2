/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 * Indicates what components should be removed
 * when the entity dies.
 * <p>
 * This is not technically safe to use to remove some components,
 * which would be better off removed in their own states, but it
 * should work ok for many other components.
 * 
 * @author codex
 */
public class RemoveOnDeath implements EntityComponent {
    
    private final Class[] components;

    public RemoveOnDeath(Class... components) {
        this.components = components;
    }

    public Class[] getComponents() {
        return components;
    }
    
}
