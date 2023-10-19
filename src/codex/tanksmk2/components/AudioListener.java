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
public class AudioListener implements EntityComponent {
    
    public static final AudioListener INSTANCE = new AudioListener(0);
    
    private final int priority;

    public AudioListener(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
    @Override
    public String toString() {
        return "AudioListener{" + "priority=" + priority + '}';
    }
    
}
