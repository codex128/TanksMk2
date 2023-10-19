/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 * @param <T>
 */
public interface Factory <T> {
    
    public T load(FactoryInfo info, EntityId customer);
    
}
