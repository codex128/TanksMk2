/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.input;

import codex.tanksmk2.components.AimDirection;
import codex.tanksmk2.components.ControllerHardwareIndex;
import codex.tanksmk2.components.TargetMove;
import codex.tanksmk2.util.GameUtils;
import com.jme3.input.InputManager;
import com.jme3.input.Joystick;
import com.jme3.input.RawInputListener;
import com.jme3.math.Vector2f;
import com.simsilica.es.Entity;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.input.AnalogFunctionListener;
import com.simsilica.lemur.input.FunctionId;
import com.simsilica.lemur.input.InputMapper;
import com.simsilica.lemur.input.InputState;
import com.simsilica.lemur.input.StateFunctionListener;

/**
 * Publishes tank inputs based on gamepad input.
 * 
 * @author codex
 */
public class GamepadPublisher implements PlayerInputPublisher,
        StateFunctionListener, AnalogFunctionListener {

    private final InputMapper inputMapper;
    private final Entity entity;
    private TankInputFunctions functions;
    
    public GamepadPublisher(InputMapper im, Entity entity) {
        this.inputMapper = im;
        this.entity = entity;
        //joystick = inputManager.getJoysticks()[entity.get(ControllerHardwareIndex.class).getIndex()];
    }
    
    @Override
    public void onEnable() {
        inputMapper.activateGroup(functions.getGroupName());
        inputMapper.addAnalogListener(this, functions.getFunctions());
    }
    @Override
    public void onDisable() {
        inputMapper.deactivateGroup(functions.getGroupName());
        inputMapper.removeAnalogListener(this, functions.getFunctions());
    }
    @Override
    public void valueChanged(FunctionId func, InputState value, double tpf) {}
    @Override
    public void valueActive(FunctionId func, double value, double tpf) {
        if (func == functions.getMoveX()) {
            PlayerInputPublisher.setTankMoveDirection(entity, Axis.X, (float)value);
        }
        else if (func == functions.getMoveY()) {
            PlayerInputPublisher.setTankMoveDirection(entity, Axis.Z, (float)value);
        }
        else if (func == functions.getAimX()) {
            PlayerInputPublisher.setTankAimDirection(entity, Axis.X, (float)value);
        }
        else if (func == functions.getAimY()) {
            PlayerInputPublisher.setTankAimDirection(entity, Axis.Z, (float)value);
        }
        else if (func == functions.getShoot()) {
            // publish shoot event
        }
    }
    
    
    
}
