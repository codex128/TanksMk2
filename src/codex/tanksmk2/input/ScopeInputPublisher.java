/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.input;

import codex.tanksmk2.components.EquipedGuns;
import codex.tanksmk2.components.LaserEmitter;
import codex.tanksmk2.util.GameUtils;
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
public class ScopeInputPublisher implements PlayerInputPublisher, StateFunctionListener {
    
    private final EntityData ed;
    private final InputMapper inputMapper;
    private final Entity entity;
    private final TankInputFunctions functions;

    public ScopeInputPublisher(EntityData ed, InputMapper inputMapper, Entity entity, TankInputFunctions functions) {
        this.ed = ed;
        this.inputMapper = inputMapper;
        this.entity = entity;
        this.functions = functions;
    }
    
    @Override
    public void onEnable() {
        inputMapper.addStateListener(this, functions.getScope());
    }
    @Override
    public void onDisable() {
        inputMapper.removeStateListener(this, functions.getScope());
    }
    @Override
    public void update(float tpf) {}
    @Override
    public Functions getFunctions() {
        return functions;
    }
    @Override
    public void valueChanged(FunctionId func, InputState value, double tpf) {
        if (func == functions.getScope()) {
            GameUtils.onComponentExists(ed, entity.getId(), EquipedGuns.class, c -> {
                for (var g : c.getGuns()) {
                    if (value != InputState.Off) {
                        ed.setComponent(g, LaserEmitter.INSTANCE);
                    } else {
                        ed.removeComponent(g, LaserEmitter.class);
                    }
                }
            });
        }
    }
    
}
