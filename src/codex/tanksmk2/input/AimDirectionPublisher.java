/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.input;

import codex.tanksmk2.components.LookAt;
import codex.tanksmk2.util.GameUtils;
import com.jme3.math.Vector3f;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.lemur.input.AnalogFunctionListener;
import com.simsilica.lemur.input.FunctionId;
import com.simsilica.lemur.input.InputMapper;

/**
 *
 * @author codex
 */
public class AimDirectionPublisher implements PlayerInputPublisher, AnalogFunctionListener {
    
    private final EntityData ed;
    private final InputMapper im;
    private final Entity entity;
    private final TankInputFunctions functions;
    private final Vector3f input = new Vector3f();

    public AimDirectionPublisher(EntityData ed, InputMapper im, Entity entity, TankInputFunctions functions) {
        this.ed = ed;
        this.im = im;
        this.entity = entity;
        this.functions = functions;
    }
    
    @Override
    public void onEnable() {
        //im.activateGroup(group);
        im.addAnalogListener(this, functions.getAimX(), functions.getAimY());
    }
    @Override
    public void onDisable() {
        //im.deactivateGroup(group);
        im.removeAnalogListener(this, functions.getAimX(), functions.getAimY());
    }
    @Override
    public void update(float tpf) {
        if (GameUtils.isDead(ed, entity.getId())) {
            return;
        }
        if (!ed.getComponent(entity.getId(), LookAt.class).getVector().equals(input)) {
            ed.setComponent(entity.getId(), new LookAt(input));
        }
    }
    @Override
    public void valueActive(FunctionId func, double value, double tpf) {
        if (func == functions.getAimX()) {
            input.setX((float)value);
        }
        else if (func == functions.getAimY()) {
            input.setY((float)value);
        }
    }
    @Override
    public Functions getFunctions() {
        return functions;
    }
    
}
