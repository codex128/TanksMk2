/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.GameObject;
import codex.tanksmk2.components.Parent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.es.common.Decay;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class ParentSystem extends AbstractGameSystem {

    private EntityData ed;
    private EntitySet entities;
    private final double updateInterval = .1;
    private long nextUpdate = -1;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        entities = ed.getEntities(Parent.class);
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        if (nextUpdate < 0) {
            nextUpdate = time.getFutureTime(updateInterval);
        }
        else if (time.getTime() >= nextUpdate) {
            entities.applyChanges();
            for (var e : entities) {
                if (ed.getComponent(e.get(Parent.class).getId(), GameObject.class) == null) {
                    if (e.get(Parent.class).isRemoveOnMiss()) {
                        ed.setComponent(e.getId(), new Decay(time.getTime()));
                    }
                    ed.removeComponent(e.getId(), Parent.class);
                }
            }
            nextUpdate = time.getFutureTime(updateInterval);
        }
    }
    
}
