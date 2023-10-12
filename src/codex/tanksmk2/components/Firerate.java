/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class Firerate implements EntityComponent {
    
    private final long ready;
    
    public Firerate(long ready) {
        this.ready = ready;
    }

    public long getTimeReady() {
        return ready;
    }
    public boolean isComplete(SimTime time) {
        return ready <= time.getTime();
    }
    
}
