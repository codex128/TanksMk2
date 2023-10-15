/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.collision;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsRayTestResult;
import com.jme3.math.Ray;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class SegmentedRaytest implements Iterable<PhysicsRayTestResult> {
    
    private final PhysicsSpace space;
    private final EntityData ed;
    private EntityId user;
    private Ray ray;
    private ShapeFilter filter;
    
    public SegmentedRaytest(PhysicsSpace space, EntityData ed, EntityId user, Ray ray) {
        this(space, ed, user, ray, ShapeFilter.OPEN);
    }
    
    public SegmentedRaytest(PhysicsSpace space, EntityData ed, EntityId user, Ray ray, ShapeFilter filter) {
        this.space = space;
        this.ed = ed;
        this.user = user;
        this.ray = ray;
        this.filter = filter;
    }
    
    @Override
    public RaySegmentIterator iterator() {
        return new RaySegmentIterator(this);
    }
    
    public PhysicsSpace getSpace() {
        return space;
    }
    
    public EntityData getEntityData() {
        return ed;
    }
    
    public EntityId getUser() {
        return user != null ? user : EntityId.NULL_ID;
    }
    
    public Ray getRay() {
        return ray;
    }
    
    public ShapeFilter getFilter() {
        return filter;
    }
    
}
