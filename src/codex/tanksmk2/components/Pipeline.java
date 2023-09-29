/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

/**
 * Transfers components assigned to this entity to another entity.
 * 
 * @author codex
 */
public class Pipeline implements EntityComponent {
    
    private final EntityId target;
    private final Class[] components;

    public Pipeline(EntityId target, Class... components) {
        this.target = target;
        this.components = components;
    }

    public EntityId getTarget() {
        return target;
    }
    public Class[] getComponents() {
        return components;
    }
    @Override
    public String toString() {
        return "Pipeline{" + "target=" + target + ", components=" + components.length + '}';
    }
    
}
