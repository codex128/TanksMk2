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
public class InputChannel implements EntityComponent {
    
    public static final int MOVE = 0, AIM = 1, SHOOT = 2;
    
    private final int channel;

    public InputChannel(int channel) {
        this.channel = channel;
    }

    public int getChannel() {
        return channel;
    }
    @Override
    public String toString() {
        return "InputChannel{" + "channel=" + channel + '}';
    }
    
}
