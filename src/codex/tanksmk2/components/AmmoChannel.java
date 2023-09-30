/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 * Defines which ammo type out of the {@link Inventory} to use.
 * 
 * @author codex
 */
public class AmmoChannel implements EntityComponent {
    
    private final int channel;

    public AmmoChannel() {
        this(Inventory.BULLETS);
    }
    public AmmoChannel(int channel) {
        this.channel = channel;
    }

    public int getChannel() {
        return channel;
    }
    @Override
    public String toString() {
        return "AmmoChannel{" + "channel=" + channel + '}';
    }
    
}
