/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.Position;
import codex.tanksmk2.components.Rotation;
import codex.tanksmk2.components.WorldTransform;
import codex.tanksmk2.util.GameUtils;
import com.jme3.math.Transform;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 * Calculates world transform for each entity.
 * 
 * @author codex
 */
public class WorldTransformSystem extends AbstractGameSystem {
    
    private EntityData ed;
    private EntitySet entities;
    private final Transform transform = new Transform();
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        entities = ed.getEntities(WorldTransform.class);
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        entities.applyChanges();
        for (var e : entities) {
            var current = e.get(WorldTransform.class);
            GameUtils.getWorldTransform(ed, e.getId(), transform);
            if (!current.getTranslation().equals(transform.getTranslation())
                    || !current.getRotation().equals(transform.getRotation())) {
                e.set(new WorldTransform(transform));
            }
        }
    }
    
}
