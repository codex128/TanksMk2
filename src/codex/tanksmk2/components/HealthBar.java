/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class HealthBar implements EntityComponent {
    
    private final float length, thickness, offset;

    public HealthBar(float length, float thickness, float offset) {
        this.length = length;
        this.thickness = thickness;
        this.offset = offset;
    }

    public float getLength() {
        return length;
    }
    public float getThickness() {
        return thickness;
    }
    public float getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "HealthBar{" + "length=" + length + ", thickness=" + thickness + ", offset=" + offset + '}';
    }
    
}
