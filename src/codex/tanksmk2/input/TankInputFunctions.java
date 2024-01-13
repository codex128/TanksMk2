/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.input;

import com.jme3.input.KeyInput;
import com.simsilica.lemur.input.Axis;
import com.simsilica.lemur.input.Button;
import com.simsilica.lemur.input.FunctionId;
import com.simsilica.lemur.input.InputDevice;
import com.simsilica.lemur.input.InputMapper;
import com.simsilica.lemur.input.InputState;

/**
 *
 * @author codex
 */
public class TankInputFunctions implements Functions {
    
    public static final int FUNCTIONS = 6;
    
    private final InputDevice device;
    private FunctionId[] funcs;
    private String group;
    
    /**
     * Create input functions for keyboard+mouse.
     */
    public TankInputFunctions() {
        this(null);
    }
    
    /**
     * Create input functions for the device.
     * @param device 
     */
    public TankInputFunctions(InputDevice device) {
        this.device = device;
    }

    @Override
    public void initializeFunctionIds(String group) {
        funcs = new FunctionId[FUNCTIONS];
        funcs[0] = create(group, "moveX");
        funcs[1] = create(group, "moveY");
        funcs[2] = create(group, "aimX");
        funcs[3] = create(group, "aimY");
        funcs[4] = create(group, "shoot");
        funcs[5] = create(group, "scope");
        this.group = group;
    }
    @Override
    public void initializeDefaultMappings(InputMapper im) {
        //var device = InputDevice.JOYSTICK1;
        //device.button(...)
        //device.axis(...)
        if (device == null) {
            im.map(funcs[1], InputState.Positive, KeyInput.KEY_W);
            im.map(funcs[1], InputState.Negative, KeyInput.KEY_S);
            im.map(funcs[0], InputState.Negative, KeyInput.KEY_D);
            im.map(funcs[0], InputState.Positive, KeyInput.KEY_A);
            im.map(funcs[4], Button.MOUSE_BUTTON1);
            im.map(funcs[5], Button.MOUSE_BUTTON2);
            /*
             * Keyboard does not have imbedded aim controls.
             * It will instead use the mouse position.
             */
        }
        else {
            im.map(funcs[0], device.axis(Axis.JOYSTICK_X));
            im.map(funcs[1], device.axis(Axis.JOYSTICK_Y));
            im.map(funcs[2], device.axis(new Axis("joystick_rx", "Right Stick X")));
            im.map(funcs[3], device.axis(new Axis("joystick_ry", "Right Stick Y")));
            im.map(funcs[4], device.button(Button.JOYSTICK_BUTTON1));
            im.map(funcs[5], device.button(Button.JOYSTICK_BUTTON2));
        }
    }
    @Override
    public String getGroupName() {
        return group;
    }
    @Override
    public FunctionId[] getFunctions() {
        return funcs;
    }    
    @Override
    public InputDevice getDevice() {
        return device;
    }

    public FunctionId getMoveX() {
        return funcs[0];
    }
    public FunctionId getMoveY() {
        return funcs[1];
    }
    public FunctionId getAimX() {
        return funcs[2];
    }
    public FunctionId getAimY() {
        return funcs[3];
    }
    public FunctionId getShoot() {
        return funcs[4];
    }
    public FunctionId getScope() {
        return funcs[5];
    }
    
    public static TankInputFunctions forKeyboard() {
        return new TankInputFunctions();
    }
    public static TankInputFunctions forDevice(InputDevice device) {
        return new TankInputFunctions(device);
    }
    private static FunctionId create(String group, String name) {
        return new FunctionId(group, group+":"+name);
    }
    
}
