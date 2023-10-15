/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.collision;

import com.jme3.bullet.collision.PhysicsRayTestResult;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.simsilica.bullet.EntityPhysicsObject;
import java.util.Iterator;

/**
 *
 * @author codex
 */
public class RaySegmentIterator implements Iterator<PhysicsRayTestResult> {
    
    private static final float FAR = 100f;
    
    private final SegmentedRaytest raytest;
    private final Ray ray = new Ray();
    private PhysicsRayTestResult closest;
    private float probeDistance = -1;
    private float contactDistance;
    private float distanceTraveled = 0f;
    private int iterationsMade = 0;
    private boolean miss = false;
    
    public RaySegmentIterator(SegmentedRaytest raytest) {
        this.raytest = raytest;
        initialize();
    }
    
    private void initialize() {
        ray.set(raytest.getRay());
    }
    
    @Override
    public boolean hasNext() {
        return !miss;
    }
    
    @Override
    public PhysicsRayTestResult next() {
        float d = getProbeDistance();
        var results = raytest.getSpace().rayTest(ray.getOrigin(), ray.getOrigin().add(ray.getDirection().mult(d)));
        closest = null;
        contactDistance = 0;
        for (var r : results) {
            if (r.getCollisionObject() instanceof EntityPhysicsObject
                    && raytest.getFilter().filter(raytest.getEntityData(), raytest.getUser(), ((EntityPhysicsObject)r.getCollisionObject()).getId())
                    && (closest == null || r.getHitFraction() < closest.getHitFraction())) {
                closest = r;
            }
        }
        if (closest != null) {
            contactDistance = d*closest.getHitFraction();
            ray.origin.addLocal(ray.getDirection().mult(contactDistance));
            distanceTraveled += contactDistance;
        }
        else {
            miss = true;
            ray.origin.addLocal(ray.getDirection().mult(d));
            distanceTraveled += d;
        }
        iterationsMade++;
        probeDistance = -1;
        return closest;
    }

    public PhysicsRayTestResult getClosestResult() {
        return closest;
    }
    
    public Vector3f getContactPoint() {
        return ray.getOrigin().clone();
    }
    
    public Vector3f getNextDirection() {
        return ray.getDirection();
    }
    
    public float getContactDistance() {
        return contactDistance;
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
        return !miss;
    }
    
    public void setNextDirection(Vector3f direction) {
        ray.setDirection(direction);        
    }
    
    public void setNextProbeDistance(float distance) {
        probeDistance = distance;
    }
    
}
