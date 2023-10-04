/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.input;

import codex.tanksmk2.components.TriggerInput;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.lemur.input.FunctionId;
import com.simsilica.lemur.input.InputMapper;
import com.simsilica.lemur.input.InputState;
import com.simsilica.lemur.input.StateFunctionListener;

/**
 *
 * @author codex
 */
public class TriggerInputPublisher implements PlayerInputPublisher, StateFunctionListener { 
    
    private final EntityData ed;
    private final Entity entity;
    private final InputMapper inputMapper;
    private final TankInputFunctions functions;

    public TriggerInputPublisher(EntityData ed, Entity entity, InputMapper inputMapper, TankInputFunctions functions) {
        this.ed = ed;
        this.entity = entity;
        this.inputMapper = inputMapper;
        this.functions = functions;
    }
    
    @Override
    public void onEnable() {
        inputMapper.addStateListener(this, functions.getShoot());
    }
    @Override
    public void onDisable() {
        inputMapper.removeStateListener(this, functions.getShoot());
    }
    @Override
    public void update(float tpf) {}
    @Override
    public Functions getFunctions() {
        return functions;
    }
    @Override
    public void valueChanged(FunctionId func, InputState value, double tpf) {
        ed.setComponent(entity.getId(), new TriggerInput(value != InputState.Off));
    }
    
}
