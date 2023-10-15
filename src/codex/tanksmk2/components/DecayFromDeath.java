/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import codex.tanksmk2.util.GameUtils;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.common.Decay;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class DecayFromDeath implements EntityComponent {
    
    private final double duration;
    private final boolean force;

    public DecayFromDeath(double duration) {
        this(duration, true);
    }
    public DecayFromDeath(double duration, boolean force) {
        this.duration = duration;
        this.force = force;
    }

    public double getDuration() {
        return duration;
    }
    public boolean isForceDecay() {
        return force;
    }
    public Decay generateDecay(SimTime time) {
        return GameUtils.duration(time, duration);
    }
    @Override
    public String toString() {
        return "DecayFromDeath{" + "duration=" + duration + ", force=" + force + '}';
    }
    
}
