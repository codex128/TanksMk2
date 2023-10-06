/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.jme3.shader.VarType;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class MatValue implements EntityComponent {
    
    private final String name;
    private final VarType type;
    private final Object value;

    public MatValue(String name, VarType type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }
    public VarType getType() {
        return type;
    }
    public Object getValue() {
        return value;
    }
    
}
