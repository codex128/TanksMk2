/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.bullet;

import codex.tanksmk2.components.Velocity;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.math.Vector3f;
import com.simsilica.bullet.ControlDriver;
import com.simsilica.bullet.EntityPhysicsObject;
import com.simsilica.bullet.EntityRigidBody;
import com.simsilica.es.Entity;
import com.simsilica.sim.SimTime;

/**
 * Converts entity components to physical properties for tanks.
 * 
 * @author codex
 */
public class TankPhysicsDriver implements ControlDriver {
    
    private final Entity entity;
    private EntityRigidBody body;
    
    public TankPhysicsDriver(Entity entity) {
        this.entity = entity;
    }
    
    @Override
    public void initialize(EntityRigidBody body) {
        this.body = body;
        setBodyProperties();
    }
    @Override
    public void update(SimTime time, EntityRigidBody body) {
        var velocity = entity.get(Velocity.class);
        if (velocity.getMagnitude() > 0) {
            body.setLinearVelocity(velocity.getVelocity());
            //entity.set(velocity.setMagnitude(0f));
        }
    }
    @Override
    public void terminate(EntityRigidBody body) {}
    @Override
    public void addCollision(EntityPhysicsObject otherBody, PhysicsCollisionEvent event) {}
    
    private void setBodyProperties() {
        body.setGravity(new Vector3f(0f, -100f, 0f));
        body.setAngularFactor(0f);
    }
    
}
