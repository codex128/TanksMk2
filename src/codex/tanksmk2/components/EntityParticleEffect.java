/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import codex.tanksmk2.factories.Prefab;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class EntityParticleEffect implements EntityComponent {
    
    private final Object key;
    private final Prefab prefab;
    private final boolean control;

    public EntityParticleEffect(Prefab prefab) {
        this(null, prefab, true);
    }
    public EntityParticleEffect(Object key, Prefab prefab) {
        this(key, prefab, true);
    }
    public EntityParticleEffect(Object key, Prefab prefab, boolean control) {
        this.key = key;
        this.prefab = prefab;
        this.control = control;
    }

    public Object getKey() {
        return key;
    }
    public Object getKey(EntityId id) {
        if (key != null) {
            return key;
        } else {
            return id;
        }
    }
    public Prefab getPrefab() {
        return prefab;
    }
    public boolean isControlling() {
        return control;
    }

    @Override
    public String toString() {
        return "EntityParticleEffect{" + "key=" + key + ", prefab=" + prefab + '}';
    }
    
}
