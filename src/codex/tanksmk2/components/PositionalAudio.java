/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 * Indicates that the audio entity is positional.
 * 
 * @author codex
 */
public class PositionalAudio implements EntityComponent {
    
    private final boolean directional, reverb;
    
    public PositionalAudio() {
        this(false, false);
    }
    public PositionalAudio(boolean directional, boolean reverb) {
        this.directional = directional;
        this.reverb = reverb;
    }

    public boolean isDirectional() {
        return directional;
    }
    public boolean isReverb() {
        return reverb;
    }
    
}
