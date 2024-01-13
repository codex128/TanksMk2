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
import java.util.Iterator;

/**
 *
 * @author codex
 */
public class RaySegmentIterator implements Iterator<RaycastResult> {
    
    /**
     * A generally far distance.
     * <p>
 Used as the raycast distance if no distance is specified by the user.
     */
    private static final float FAR = 1000;
    
    private final SegmentedRaycast raycast;
    private final Ray ray = new Ray();
    private final Segment segment = new Segment();
    private RaycastResult closest;
    private float probeDistance = -1;
    private float distanceTraveled = 0f;
    private int iterationsMade = 0;
    private boolean miss = false;
    
    public RaySegmentIterator(SegmentedRaycast raytest) {
        this.raycast = raytest;
        ray.set(raytest.getRay());
    }
    
    @Override
    public boolean hasNext() {
        return !miss;
    }    
    @Override
    public RaycastResult next() {
        float d = getProbeDistance();
        segment.set(ray, d);
        var result = raycast.getRaycaster().raycast(raycast.getEntityData(), segment, raycast.getUser(), raycast.getFilter());
        if (closest != null) {
            closest = result;
            ray.origin.set(closest.getCollisionPoint());
            distanceTraveled += closest.getCollisionDistance();
        } else {
            miss = true;
            ray.origin.addLocal(ray.getDirection().mult(d));
            distanceTraveled += d;
        }
        iterationsMade++;
        probeDistance = -1;
        return result;
    }
    
    public RaycastResult getClosestResult() {
        return closest;
    }
    public EntityId getCollisionEntity() {
        return closest.getCollisionEntity();
    }
    public SegmentedRaycast getRaycast() {
        return raycast;
    }    
    public Vector3f getContactPoint() {
        return ray.getOrigin().clone();
    } 
    public Vector3f getNextDirection() {
        return ray.getDirection();
    }    
    public float getContactDistance() {
        return closest.getCollisionDistance();
    }    
    public float getDistanceTraveled() {
        return distanceTraveled;
    }    
    public float getProbeDistance() {
        return probeDistance > 0 ? probeDistance : FAR;
    }    
    public int getNumIterations() {
        return iterationsMade;
    }
    public boolean collisionOccured() {
        return !miss && closest != null;
    }
    
    public void setNextDirection(Vector3f direction) {
        ray.setDirection(direction);        
    }    
    public void setNextDistance(float distance) {
        probeDistance = distance;
    }
    public void setNextPosition(Vector3f position) {
        ray.setOrigin(position);
    }
    public float advanceProbePosition(float length) {
        probeDistance -= length;
        ray.origin.addLocal(ray.direction.mult(length));
        return probeDistance;
    }
    
}
