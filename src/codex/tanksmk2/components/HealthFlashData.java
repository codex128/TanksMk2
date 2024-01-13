/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.jme3.math.ColorRGBA;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class HealthFlashData implements EntityComponent {
    
    private final ColorRGBA color = new ColorRGBA();
    private final double startTime, duration;

    public HealthFlashData(ColorRGBA color, double startTime, double duration) {
        this.color.set(color);
        this.startTime = startTime;
        this.duration = duration;
    }

    public ColorRGBA getColor() {
        return color;
    }
    public double getStartTime() {
        return startTime;
    }
    public double getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "HealthFlashColor{" + "color=" + color + '}';
    }
    
}
