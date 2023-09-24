/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.input;

import com.jme3.input.KeyInput;
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
    
    //private FunctionId moveX, moveY, aimX, aimY, shoot;
    private FunctionId[] funcs;
    private String group;
    
    public TankInputFunctions() {
        
    }

    @Override
    public void initializeFunctionIds(String group) {
        funcs = new FunctionId[5];
        //funcs[0] = new FunctionId(group, "moveX");
        //funcs[1] = new FunctionId(group, "moveY");
        //funcs[2] = new FunctionId(group, "aimX");
        //funcs[3] = new FunctionId(group, "aimY");
        //funcs[4] = new FunctionId(group, "shoot");
        this.group = group;
    }
    @Override
    public void initializeDefaultMappings(InputMapper im) {
        im.map(funcs[1], InputState.Positive, KeyInput.KEY_W);
        im.map(funcs[1], InputState.Negative, KeyInput.KEY_S);
        im.map(funcs[0], InputState.Negative, KeyInput.KEY_D);
        im.map(funcs[0], InputState.Positive, KeyInput.KEY_A);
        im.map(funcs[4], Button.MOUSE_BUTTON1);
    }
    @Override
    public String getGroupName() {
        return group;
    }
    @Override
    public FunctionId[] getFunctions() {
        return funcs;
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
    
}
