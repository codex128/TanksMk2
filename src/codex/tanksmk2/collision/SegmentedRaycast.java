/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.collision;

import com.jme3.math.Ray;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class SegmentedRaycast implements Iterable<RaycastResult> {
    
    public static final float ADV_DIST = 0.001f;
    
    private final EntityData ed;
    private final Raycaster raycaster;
    private final EntityId user;
    private final Ray ray;
    private ShapeFilter filter;
    
    public SegmentedRaycast(EntityData ed, Raycaster raycaster, EntityId user, Ray ray) {
        this(ed, raycaster, user, ray, null);
    }    
    public SegmentedRaycast(EntityData ed, Raycaster raycaster, EntityId user, Ray ray, ShapeFilter filter) {
        this.ed = ed;
        this.raycaster = raycaster;
        this.user = user;
        this.ray = ray;
        this.filter = filter;
    }
    
    @Override
    public RaySegmentIterator iterator() {
        return new RaySegmentIterator(this);
    } 
    
    public void setFilter(ShapeFilter filter) {
        this.filter = filter;
    }
    
    public EntityData getEntityData() {
        return ed;
    }
    public Raycaster getRaycaster() {
        return raycaster;
    }
    public EntityId getUser() {
        return user != null ? user : EntityId.NULL_ID;
    }
    public Ray getRay() {
        return ray;
    }
    public ShapeFilter getFilter() {
        return (filter != null ? filter : ShapeFilter.Open);
    }
    
}
