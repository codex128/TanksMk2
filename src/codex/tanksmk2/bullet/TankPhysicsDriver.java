/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.bullet;

import codex.tanksmk2.components.Stats;
import codex.tanksmk2.components.TankMoveDirection;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.math.Vector3f;
import com.simsilica.bullet.ControlDriver;
import com.simsilica.bullet.EntityPhysicsObject;
import com.simsilica.bullet.EntityRigidBody;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.sim.SimTime;

/**
 * Converts entity components to physical properties for tanks.
 * 
 * @author codex
 */
public class TankPhysicsDriver implements ControlDriver {
    
    private final EntityData ed;
    private final Entity entity;
    private EntityRigidBody body;
    private final Vector3f currentMove = new Vector3f();
    private final Vector3f targetMove = new Vector3f();
    
    public TankPhysicsDriver(EntityData ed, Entity entity) {
        this.ed = ed;
        this.entity = entity;
    }
    
    @Override
    public void initialize(EntityRigidBody body) {
        this.body = body;
        setBodyProperties();
    }
    @Override
    public void update(SimTime time, EntityRigidBody body) {
        var stats = entity.get(Stats.class);
        targetMove.set(entity.get(TankMoveDirection.class).getDirection()).multLocal(stats.get(Stats.MOVE_SPEED));
        targetMove.setY(0);
        if (!targetMove.equals(Vector3f.ZERO)) {
            body.getLinearVelocity(currentMove).setY(0);
            currentMove.addLocal(targetMove.subtract(currentMove).normalizeLocal().multLocal(stats.get(Stats.MOVE_ACCEL)));
            body.setLinearVelocity(currentMove.setY(body.getLinearVelocity().y));
        }
    }
    @Override
    public void terminate(EntityRigidBody body) {
        body.setAngularFactor(1f);
    }
    @Override
    public void addCollision(EntityPhysicsObject otherBody, PhysicsCollisionEvent event) {}
    
    private void setBodyProperties() {
        body.setGravity(new Vector3f(0f, -100f, 0f));
        body.setAngularFactor(0f);
        body.setFriction(0.8f);
        body.setLinearDamping(0.9f);
    }
    
}
