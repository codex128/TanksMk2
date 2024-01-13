/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.MatValue;
import codex.tanksmk2.components.Outline;
import codex.tanksmk2.components.TargetTo;
import codex.tanksmk2.util.GameUtils;
import com.jme3.shader.VarType;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class OutlineSystem extends AbstractGameSystem {
    
    private static final String OUTLINE_PARAM = "OutlineColor";
    
    private EntityData ed;
    private EntitySet entities;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        entities = ed.getEntities(Outline.class);
    }    
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        if (entities.applyChanges()) {
            entities.getAddedEntities().forEach(e -> updateMaterialValue(time, e));
            entities.getChangedEntities().forEach(e -> updateMaterialValue(time, e));
            entities.getRemovedEntities().forEach(e -> terminateMaterialValue(time, e));
        }
    }
    
    private void updateMaterialValue(SimTime time, Entity e) {
        var mat = ed.createEntity();
        ed.setComponents(mat,
            new MatValue(OUTLINE_PARAM, VarType.Vector4, e.get(Outline.class).getColor()),
            new TargetTo(e.getId()),
            GameUtils.duration(time, 1.0)
        );
    }
    private void terminateMaterialValue(SimTime time, Entity e) {
        var mat = ed.createEntity();
        ed.setComponents(mat,
            new MatValue(OUTLINE_PARAM, VarType.Vector4, null),
            new TargetTo(e.getId()),
            GameUtils.duration(time, 1.0)
        );
    }
    
}
