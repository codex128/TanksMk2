/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.input;

import codex.tanksmk2.components.TargetMove;
import com.jme3.math.Vector3f;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
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
public class MoveInputPublisher implements PlayerInputPublisher,
        StateFunctionListener, AnalogFunctionListener {

    public static final String ID = MoveInputPublisher.class.getName();
    
    private final EntityData ed;
    private final InputMapper inputMapper;
    private final Entity entity;
    private final TankInputFunctions functions;
    private final Vector3f input = new Vector3f();
    
    public MoveInputPublisher(EntityData ed, InputMapper im, Entity entity, TankInputFunctions functions) {
        this.ed = ed;
        this.inputMapper = im;
        this.entity = entity;
        this.functions = functions;
    }
    
    @Override
    public void onEnable() {
        //inputMapper.activateGroup(functions.getGroupName());
        inputMapper.addAnalogListener(this, functions.getMoveX(), functions.getMoveY());
    }
    @Override
    public void onDisable() {
        //inputMapper.deactivateGroup(functions.getGroupName());
        inputMapper.removeAnalogListener(this, functions.getMoveX(), functions.getMoveY());
    }
    @Override
    public void update(float tpf) {
        var target = ed.getComponent(entity.getId(), TargetMove.class);
        if (target == null || !target.getDirection().equals(input)) {
            ed.setComponent(entity.getId(), new TargetMove(input));
        }
        input.set(Vector3f.ZERO);
    }
    @Override
    public void valueChanged(FunctionId func, InputState value, double tpf) {}
    @Override
    public void valueActive(FunctionId func, double value, double tpf) {
        if (func == functions.getMoveX()) {
            input.setX((float)value);
        }
        else if (func == functions.getMoveY()) {
            input.setZ((float)value);
        }
    }
    @Override
    public Functions getFunctions() {
        return functions;
    }
    
}
