/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package codex.tanksmk2.curves;

import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public interface Interpolator {
    
    public float interpolate(float t);
    
    public default float interpolate(double t) {
        return interpolate((float)t);
    }
    
    public default float interpolate(float start, float t) {
        return interpolate(t-start);
    }
    
    public default float interpolate(double start, double t) {
        return interpolate((float)(t-start));
    }
    
    public default float interpolate(double start, SimTime time) {
        return interpolate((float)(time.getTimeInSeconds()-start));
    }
    
}
