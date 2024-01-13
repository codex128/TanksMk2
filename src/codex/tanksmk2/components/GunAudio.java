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
public class GunAudio implements EntityComponent {
    
    public static final int BANG = 0;    
    private final EntityId[] audio;

    public GunAudio(EntityId... audio) {
        this.audio = audio;
    }

    public EntityId[] getAudio() {
        return audio;
    }
    public EntityId get(int i) {
        return audio[i];
    }
    
}
