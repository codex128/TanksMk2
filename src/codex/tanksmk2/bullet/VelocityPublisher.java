/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.bullet;

import codex.tanksmk2.components.AngularVelocity;
import codex.tanksmk2.components.LinearVelocity;
import com.simsilica.bullet.EntityPhysicsObject;
import com.simsilica.bullet.EntityRigidBody;
import com.simsilica.bullet.PhysicsObjectListener;
import com.simsilica.es.EntityData;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class VelocityPublisher implements PhysicsObjectListener {
    
    private final EntityData ed;

    public VelocityPublisher(EntityData ed) {
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
        if (object instanceof EntityRigidBody) {
            var rigidbody = (EntityRigidBody)object;
            ed.setComponents(object.getId(),
                new LinearVelocity(rigidbody.getLinearVelocity()),
                new AngularVelocity(rigidbody.getAngularVelocity())
            );
        }
    }
    @Override
    public void removed(EntityPhysicsObject object) {}
    
}
