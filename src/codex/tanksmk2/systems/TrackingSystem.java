/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.LookAt;
import codex.tanksmk2.components.AxisConstraint;
import codex.tanksmk2.components.Rotation;
import codex.tanksmk2.util.GameUtils;
import com.jme3.math.Vector3f;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 * Rotates an entity to look at a position or direction.
 * 
 * @author codex
 */
public class TrackingSystem extends AbstractGameSystem {
    
    private EntityData ed;
    private EntitySet entities;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        entities = ed.getEntities(Rotation.class, LookAt.class);
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        if (entities.applyChanges()) {
            entities.getAddedEntities().forEach(e -> update(e));
            entities.getChangedEntities().forEach(e -> update(e));
        }
    }
    
    private void update(Entity e) {
        if (e.get(LookAt.class).isLocal()) {
            updateLocal(e);
        }
        else {
            updateWorld(e);
        }
    }
    private void updateLocal(Entity e) {
        e.set(new Rotation(getConstraint(e.getId()).applyLocal(e.get(LookAt.class).getVector().normalize(), 0f), Vector3f.UNIT_Y));
    }
    private void updateWorld(Entity e) {
        e.set(new Rotation(getConstraint(e.getId()).applyLocal(e.get(LookAt.class).getVector().subtract(GameUtils.getWorldTransform(ed, e).getTranslation()).normalizeLocal(), 0f), Vector3f.UNIT_Y));
    }
    
    private AxisConstraint getConstraint(EntityId id) {
        var ac = ed.getComponent(id, AxisConstraint.class);
        if (ac == null) return AxisConstraint.ALL;
        return ac;
    }
    
}
