/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package codex.tanksmk2.input;

/**
 *
 * @author codex
 */
public enum ControllerType {
    
    Keyboard("keyboard+mouse"), Gamepad("gamepad");
    
    private final String name;
    private ControllerType(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "ControllerType{" + name + '}';
    }
    
}
