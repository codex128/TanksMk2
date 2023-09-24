/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.EntityTransform;
import codex.tanksmk2.input.PlayerInputPublisher;
import codex.tanksmk2.input.TankInputFunctions;
import codex.tanksmk2.util.GameUtils;
import com.jme3.input.InputManager;
import com.jme3.math.Plane;
import com.jme3.math.Ray;
import com.jme3.math.Transform;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.simsilica.es.Entity;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.input.AnalogFunctionListener;
import com.simsilica.lemur.input.FunctionId;
import com.simsilica.lemur.input.InputMapper;
import com.simsilica.sim.SimTime;

/**
 * Publishes tank inputs based on keyboard and cursor input.
 * 
 * @author codex
 */
public class KeyboardPublisher implements PlayerInputPublisher, AnalogFunctionListener {
    
    private final InputManager inputManager;
    private final InputMapper inputMapper;
    private final Entity entity;
    private final Camera camera;
    private TankInputFunctions functions;
    private Ray pickRay;
    private final Transform lastCamTransform = new Transform();
    private final Vector2f lastCursorPosition = new Vector2f();

    public KeyboardPublisher(InputManager inputManager, InputMapper inputMapper, Entity entity, Camera camera) {
        this.inputManager = inputManager;
        this.inputMapper = inputMapper;
        this.entity = entity;
        this.camera = camera;
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
    public void update(SimTime time) {
        updatePickRay();
        
    }
    @Override
    public void valueActive(FunctionId func, double value, double tpf) {
        if (func == functions.getMoveX()) {
            PlayerInputPublisher.setTankMoveDirection(entity, Axis.X, (float)value);
        }
        else if (func == functions.getMoveY()) {
            PlayerInputPublisher.setTankMoveDirection(entity, Axis.Y, (float)value);
        }
        else if (func == functions.getShoot()) {
            // publish shoot event
        }
    }
    
    private void updatePickRay() {
        if (pickRay == null 
                || !camera.getLocation().equals(lastCamTransform.getTranslation())
                || !camera.getRotation().equals(lastCamTransform.getRotation())
                || !inputManager.getCursorPosition().equals(lastCursorPosition)) {
            pickRay = GameUtils.getCursorPickRay(camera, inputManager.getCursorPosition());
            lastCamTransform.setTranslation(camera.getLocation());
            lastCamTransform.setRotation(camera.getRotation());
            lastCursorPosition.set(inputManager.getCursorPosition());
        }
    }
    
}
