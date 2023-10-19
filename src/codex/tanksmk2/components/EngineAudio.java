/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class EngineAudio implements EntityComponent {
    
    private final EntityId[] noises;

    public EngineAudio(EntityId... noises) {
        this.noises = noises;
    }
    
    public EntityId get(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index cannot be less than zero.");
        }
        if (i >= noises.length) {
            return null;
        }
        return noises[i];
    }
    
}
