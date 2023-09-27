/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package codex.tanksmk2.input;

import com.simsilica.lemur.input.InputDevice;

/**
 *
 * @author codex
 */
public enum DeviceType {
    
    Keyboard("keyboard+mouse"), Gamepad("gamepad");
    
    private final String name;
    private DeviceType(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "ControllerType{" + name + '}';
    }
    
    public static DeviceType fromDevice(InputDevice device) {
        return device != null ? Gamepad : Keyboard;
    }
    
}
