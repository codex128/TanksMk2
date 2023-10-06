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
    
    private final double start;
    private final double length;
    
    public Firerate(double start, double length) {
        this.start = start;
        this.length = length;
    }
    public Firerate(SimTime time, double length) {
        this(time.getTimeInSeconds(), length);
    }

    public double getStart() {
        return start;
    }
    public double getLength() {
        return length;
    }
    public boolean isComplete(SimTime time) {
        return start+length <= time.getTimeInSeconds();
    }
    
    public Firerate shoot(SimTime time) {
        return new Firerate(time, length);
    }
    
}
