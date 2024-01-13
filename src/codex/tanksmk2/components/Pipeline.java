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
    private final boolean applyToTarget;

    public Pipeline(EntityId target, Class... components) {
        this(target, true, components);
    }
    public Pipeline(EntityId target, boolean applyToTarget, Class... components) {
        this.target = target;
        this.applyToTarget = applyToTarget;
        this.components = components;
    }

    public EntityId getTarget() {
        return target;
    }
    public boolean isApplyToTarget() {
        return applyToTarget;
    }
    public Class[] getComponents() {
        return components;
    }
    @Override
    public String toString() {
        return "Pipeline{" + "target=" + target + ", components=" + components.length + '}';
    }
    
}
