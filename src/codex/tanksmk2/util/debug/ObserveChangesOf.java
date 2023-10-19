/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.util.debug;

import com.simsilica.es.EntityComponent;

/**
 * Indicates which other components should be observed for debugging when changed.
 * 
 * @author codex
 */
public class ObserveChangesOf implements EntityComponent {
    
    private final Class[] components;

    public ObserveChangesOf(Class... components) {
        this.components = components;
    }

    public Class[] getComponents() {
        return components;
    }
    
}
