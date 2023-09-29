/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import codex.tanksmk2.components.ModelInfo;
import com.simsilica.es.EntityData;

/**
 *
 * @author codex
 * @param <T>
 */
public interface Factory <T> {
    
    public default T apply(EntityData ed, ModelInfo info) {
        return load(info.getName(ed));
    }
    public T load(String name);
    
}
