/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 * Represents a channel of input (moving, aiming, shooting, etc).
 * 
 * This allows each channel to be cleanly seperated into its own entity.
 * 
 * @author codex
 */
public class InputChannels implements EntityComponent {
    
    public static final int MOVE = 0, AIM = 1, SHOOT = 2, SCOPE = 3;
    
    private final int[] channels;

    public InputChannels(int... channels) {
        this.channels = channels;
    }

    public int[] getChannels() {
        return channels;
    }
    @Override
    public String toString() {
        return "InputChannel{" + "channel=" + channels.length + '}';
    }
    
}
