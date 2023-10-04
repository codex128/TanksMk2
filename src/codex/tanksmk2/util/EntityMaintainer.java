/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.util;

import com.simsilica.es.ComponentFilter;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.SimTime;

/**
 * A simple {@link EntitySet} manager which only updates when entities are
 * added or changed.
 * 
 * @author codex
 */
public abstract class EntityMaintainer {
    
    protected final EntitySet entities;
      
    public EntityMaintainer(EntityData ed, Class... components) {
        entities = ed.getEntities(components);
    }
    public EntityMaintainer(EntityData ed, ComponentFilter filter, Class... components) {
        entities = ed.getEntities(filter, components);
    }

    public void release() {
        entities.release();
    }
    public void update(SimTime time) {
        if (entities.applyChanges()) {
            entities.getAddedEntities().forEach(e -> update(e, time));
            entities.getChangedEntities().forEach(e -> update(e, time));
        }
    }

    public abstract void update(Entity e, SimTime time);
    
}
