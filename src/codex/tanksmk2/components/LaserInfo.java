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
public class LaserInfo implements EntityComponent {
    
    private final float thickness;
    private final ColorRGBA color;

    public LaserInfo(float thickness, ColorRGBA color) {
        this.thickness = thickness;
        this.color = color;
    }

    public float getThickness() {
        return thickness;
    }
    public ColorRGBA getColor() {
        return color;
    }
    
}
