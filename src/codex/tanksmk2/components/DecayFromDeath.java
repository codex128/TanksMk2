/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.common.Decay;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class DecayFromDeath implements EntityComponent {
    
    private final long duration;
    private final boolean force;

    public DecayFromDeath(long duration) {
        this(duration, true);
    }
    public DecayFromDeath(long duration, boolean force) {
        this.duration = duration;
        this.force = force;
    }

    public long getDuration() {
        return duration;
    }
    public boolean isForceDecay() {
        return force;
    }
    public Decay generateDecay(SimTime time) {
        return Decay.duration(time.getTime(), duration);
    }
    @Override
    public String toString() {
        return "DecayFromDeath{" + "duration=" + duration + '}';
    }
    
}
