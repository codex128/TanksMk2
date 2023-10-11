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
public class LightColor implements EntityComponent {
    
    private final ColorRGBA color;

    public LightColor(ColorRGBA color) {
        this.color = color;
    }

    public ColorRGBA getColor() {
        return color;
    }
    @Override
    public String toString() {
        return "LightColor{" + color + '}';
    }
    
}
