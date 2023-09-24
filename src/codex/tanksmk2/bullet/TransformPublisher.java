/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.bullet;

import codex.tanksmk2.components.EntityTransform;
import com.simsilica.bullet.EntityPhysicsObject;
import com.simsilica.bullet.PhysicsObjectListener;
import com.simsilica.es.EntityData;
import com.simsilica.sim.SimTime;

/**
 * Publishes physics object transform via transform component.
 * 
 * @author codex
 */
public class TransformPublisher implements PhysicsObjectListener {
    
    EntityData ed;

    public TransformPublisher(EntityData ed) {
        this.ed = ed;
    }
    
    @Override
    public void startFrame(SimTime time) {}
    @Override
    public void endFrame() {}
    @Override
    public void added(EntityPhysicsObject object) {}
    @Override
    public void updated(EntityPhysicsObject object) {
        var transform = new EntityTransform(object.getPhysicsLocation(null), object.getPhysicsRotation(null));
        ed.setComponent(object.getId(), transform);
    }
    @Override
    public void removed(EntityPhysicsObject object) {}
    
}
