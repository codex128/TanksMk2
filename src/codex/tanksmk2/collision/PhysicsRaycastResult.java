/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.collision;

import com.jme3.bullet.collision.PhysicsRayTestResult;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.simsilica.bullet.EntityPhysicsObject;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class PhysicsRaycastResult implements RaycastResult {
    
    private final PhysicsRayTestResult result;
    private final Segment segment;
    private EntityPhysicsObject object;
    private Vector3f point;
    private Vector3f normal;

    public PhysicsRaycastResult(PhysicsRayTestResult result, Segment segment) {
        this.result = result;
        this.segment = segment;
    }
    
    @Override
    public EntityId getCollisionEntity() {
        if (object == null) {
            object = (EntityPhysicsObject)result.getCollisionObject();
        }
        return object.getId();
    }
    @Override
    public Vector3f getCollisionPoint() {
        if (point == null) {
            point = new Vector3f(segment.getStart()).addLocal(segment.getDirection(null)
                    .multLocal(segment.getDistance()*result.getHitFraction()));
        }
        return point;
    }
    @Override
    public Vector3f getCollisionNormal() {
        if (normal == null) {
            normal = result.getHitNormalLocal(null);
        }
        return normal;
    }
    @Override
    public float getCollisionDistance() {
        return segment.getDistance()*result.getHitFraction();
    }
    
    public PhysicsRayTestResult getResult() {
        return result;
    }
    
}
