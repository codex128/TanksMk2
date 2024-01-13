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
public class Pitch implements EntityComponent {
    
    private final float pitch;

    public Pitch(float pitch) {
        this.pitch = pitch;
    }

    public float getPitch() {
        return pitch;
    }
    @Override
    public String toString() {
        return "Pitch{" + pitch + '}';
    }
    
}
