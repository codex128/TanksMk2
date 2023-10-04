/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.Direction;
import codex.tanksmk2.components.FaceVelocity;
import codex.tanksmk2.components.Rotation;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class FaceVelocitySystem extends AbstractGameSystem {

    private EntityData ed;
    private EntitySet entities;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        entities = ed.getEntities(Rotation.class, Direction.class, FaceVelocity.class);
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
        var q = new Quaternion().lookAt(e.get(Direction.class).getDirection(), Vector3f.UNIT_Y);
        var f = e.get(FaceVelocity.class);
        if (f.hasRotationOffset()) {
            q.multLocal(f.getOffset());
        }
        e.set(new Rotation(q));
    }
    
}
