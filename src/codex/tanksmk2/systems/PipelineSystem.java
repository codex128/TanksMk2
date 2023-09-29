/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.Pipeline;
import codex.tanksmk2.util.GameUtils;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class PipelineSystem extends AbstractGameSystem {

    private EntityData ed;
    private EntitySet entities;
    
    @Override
    protected void initialize() {
        System.out.println("initialize pipeline system");
        ed = getManager().get(EntityData.class);
        entities = ed.getEntities(Pipeline.class);
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        entities.applyChanges();
        for (var e : entities) {
            var pipeline = e.get(Pipeline.class);
            if (!GameUtils.entityExists(ed, pipeline.getTarget())) {
                ed.removeComponent(e.getId(), Pipeline.class);
                continue;
            }
            for (Class type : pipeline.getComponents()) {
                var component = ed.getComponent(e.getId(), type);
                if (component != null) {
                    ed.setComponent(pipeline.getTarget(), component);
                }
            }
        }
    }
    
}
