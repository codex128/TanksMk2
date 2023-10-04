/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 * @param <T>
 */
public interface Factory <T> {
    
    public default T apply(EntityData ed, EntityId customer, Prefab prefab) {
        return load(customer, prefab.getName(ed));
    }
    public T load(EntityId customer, String name);
    
}
