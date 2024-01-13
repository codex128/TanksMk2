/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.collision;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsRayTestResult;
import com.jme3.math.Ray;
import com.simsilica.bullet.EntityPhysicsObject;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class Raycaster {
    
    private SpatialContactSpace spatialSpace;
    private VolumeContactSpace volumeSpace;
    private PhysicsSpace physicsSpace;
    private final Ray ray = new Ray();

    public Raycaster() {}
    
    public RaycastResult raycast(EntityData ed, Segment segment, EntityId user, ShapeFilter filter) {
        segment.toRay(ray);
        var results = new RaycastResult[] {
            spatialRaycast(ray, user, filter),
            volumeRaycast(ray, user, filter),
            physicsRaycast(ed, segment, user, filter),
        };
        RaycastResult closest = null;
        for (var r : results) {
            if (r != null && (closest == null || r.getCollisionDistance() < closest.getCollisionDistance())) {
                closest = r;
            }
        }
        return closest;
    }
    
    public SpatialRaycastResult spatialRaycast(Ray ray, EntityId user, ShapeFilter filter) {
        if (spatialSpace == null) {
            return null;
        }
        var closest = spatialSpace.raycast(ray, null, user, filter);
        if (closest != null) {
            return new SpatialRaycastResult(closest);
        } else {
            return null;
        }
    }
    public VolumeRaycastResult volumeRaycast(Ray ray, EntityId user, ShapeFilter filter) {
        if (volumeSpace == null) {
            return null;
        }
        return volumeSpace.raycast(ray, null, user, filter);
    }
    public PhysicsRaycastResult physicsRaycast(EntityData ed, Segment segment, EntityId user, ShapeFilter filter) {
        if (physicsSpace == null) {
            return null;
        }
        var results = physicsSpace.rayTest(segment.getStart(), segment.getEnd());
        PhysicsRayTestResult closest = null;
        for (var r : results) {
            if ((closest == null || r.getHitFraction() < closest.getHitFraction())
                    && r.getCollisionObject() instanceof EntityPhysicsObject
                    && filter.filter(ed, user, ((EntityPhysicsObject)r.getCollisionObject()).getId())) {
                closest = r;
            }
        }
        if (closest != null) {
            return new PhysicsRaycastResult(closest, segment);
        } else {
            return null;
        }
    }
    
    public void setSpace(SpatialContactSpace space) {
        this.spatialSpace = space;
    }
    public void setSpace(VolumeContactSpace space) {
        this.volumeSpace = space;
    }
    public void setSpace(PhysicsSpace space) {
        this.physicsSpace = space;
    }

    public SpatialContactSpace getSpatialSpace() {
        return spatialSpace;
    }
    public PhysicsSpace getPhysicsSpace() {
        return physicsSpace;
    }
    
}
