/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.bullet;

import codex.tanksmk2.components.Position;
import codex.tanksmk2.components.Rotation;
import codex.tanksmk2.util.GameUtils;
import com.jme3.math.Quaternion;
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
        // Physics object transform must be converted from world coordinates to local coordinates
        var pt = GameUtils.getParentWorldTransform(ed, object.getId());
        System.out.println("physics-object-rotation="+object.getPhysicsRotation(null));
        System.out.println("physics-parent-rotation="+pt.getRotation());
        ed.setComponents(object.getId(),
            new Position(object.getPhysicsLocation(null).subtractLocal(pt.getTranslation())),
            // Division of two quaternions = (q1) * (inverse of q2)
            new Rotation(object.getPhysicsRotation(null).multLocal(pt.getRotation().inverse()))
        );
    }
    @Override
    public void removed(EntityPhysicsObject object) {}
    
}
