/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.collision;

import codex.tanksmk2.util.GameUtils;
import com.jme3.collision.CollisionResult;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class SpatialRaycastResult implements RaycastResult {

    private final CollisionResult result;
    private EntityId entity;
    
    public SpatialRaycastResult(CollisionResult result) {
        this.result = result;
    }
    
    @Override
    public EntityId getCollisionEntity() {
        if (entity == null) {
            entity = GameUtils.fetchId(result.getGeometry(), -1);
        }
        return entity;
    }
    @Override
    public Vector3f getCollisionPoint() {
        return result.getContactPoint();
    }
    @Override
    public Vector3f getCollisionNormal() {
        return result.getContactNormal();
    }
    @Override
    public float getCollisionDistance() {
        return result.getDistance();
    }
    
    public CollisionResult getResult() {
        return result;
    }
    
}
