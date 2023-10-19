/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.curves;

import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public abstract class InterpolationComponent implements EntityComponent {
    
    private final double startTime;
    private final Interpolator interpolator;

    public InterpolationComponent(double start, Interpolator interpolator) {
        this.startTime = start;
        this.interpolator = interpolator;
    }

    public double getStartTime() {
        return startTime;
    }
    public Interpolator getInterpolator() {
        return interpolator;
    }
    
}
