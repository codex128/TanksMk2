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
public class EntityLight implements EntityComponent {
    
    public static final int DIRECTIONAL = 0, POINT = 1, SPOT = 2, AMBIENT = 3, PROBE = 4;
    
    private final int type;
    private final ColorRGBA color;

    public EntityLight(int type) {
        this(type, ColorRGBA.White);
    }
    public EntityLight(int type, ColorRGBA color) {
        this.type = type;
        this.color = color;
    }

    public int getType() {
        return type;
    }
    public ColorRGBA getColor() {
        return color;
    }
    
    @Override
    public String toString() {
        return "EntityLight{" + "type=" + type + '}';
    }
    
    public static int valueOf(String lightType) {
        return switch (lightType.toLowerCase()) {
            case "directional" -> DIRECTIONAL;
            case "point"       -> POINT;
            case "spot"        -> SPOT;
            case "ambient"     -> AMBIENT;
            case "probe"       -> PROBE;
            default            -> AMBIENT;
        };
    }
    
}
