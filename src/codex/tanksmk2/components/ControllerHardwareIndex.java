/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import codex.tanksmk2.input.ControllerType;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class ControllerHardwareIndex implements EntityComponent {
    
    private final ControllerType type;
    private final int index;

    public ControllerHardwareIndex(ControllerType type) {
        this(type, 0);
    }
    public ControllerHardwareIndex(ControllerType type, int index) {
        this.type = type;
        this.index = index;
    }

    public ControllerType getType() {
        return type;
    }
    public int getIndex() {
        return index;
    }
    @Override
    public String toString() {
        return "ControllerHardwareIndex{" + "type=" + type + ", index=" + index + '}';
    }
    
}
