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
public class EntityLight implements EntityComponent {
    
    public static final int DIRECTIONAL = 0, POINT = 1, SPOT = 2, AMBIENT = 3;
    
    private final int type;

    public EntityLight(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
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
            default            -> AMBIENT;
        };
    }
    
}
