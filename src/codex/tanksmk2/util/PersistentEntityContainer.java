/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.util;

import com.simsilica.es.ComponentFilter;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author codex
 */
public abstract class PersistentEntityContainer <T> {
    
    private final EntitySet entities;
    private final HashMap<EntityId, T> map = new HashMap<>();
    
    public PersistentEntityContainer(EntityData ed, Class... components) {
        entities = ed.getEntities(components);
    }
    public PersistentEntityContainer(EntityData ed, ComponentFilter filter, Class... components) {
        entities = ed.getEntities(filter, components);
    }
    
    public void update() {
        if (entities.applyChanges()) {
            addObjects(entities.getAddedEntities());
            updateChangedObjects(entities.getChangedEntities());
            removeObjects(entities.getRemovedEntities());
        }
        updateObjects(entities);
    }
    public void release() {
        removeObjects(entities);
        entities.release();
    }
    public T getObject(EntityId id) {
        return map.get(id);
    }

    private void addObjects(Set<Entity> set) {
        for (var e : set) {
            map.put(e.getId(), addObject(e));
        }
    }
    private void updateChangedObjects(Set<Entity> set) {
        for (var e : set) {
            updateObjectChanges(getObject(e.getId()), e);
        }
    }
    private void updateObjects(Set<Entity> set) {
        for (var e : set) {
            updateObject(getObject(e.getId()), e);
        }
    }
    private void removeObjects(Set<Entity> set) {
        for (var e : set) {
            removeObject(map.remove(e.getId()), e);
        }
    }

    protected abstract T addObject(Entity entity);
    protected abstract void updateObjectChanges(T t, Entity entity);
    protected abstract void updateObject(T t, Entity entity);
    protected abstract void removeObject(T t, Entity entity);
    
}
