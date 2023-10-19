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
public class AudioInput implements EntityComponent {
    
    public static final int PLAY = 0, PLAY_LOOPED = 1, PLAY_INSTANCE = 2, PAUSE = 3, STOP = 4;
    
    private final int input;

    public AudioInput(int input) {
        this.input = input;
    }

    public int getInput() {
        return input;
    }
    @Override
    public String toString() {
        return "AudioInput{" + "input=" + input + '}';
    }
    
}
