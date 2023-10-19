/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class Volume implements EntityComponent {
    
    private final float volume;

    public Volume(float volume) {
        this.volume = volume;
    }

    public float getVolume() {
        return volume;
    }
    @Override
    public String toString() {
        return "Volume{" + volume + '}';
    }
    
}
