/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.collision;

import codex.tanksmk2.components.VolumeContactShape;
import codex.tanksmk2.util.GameUtils;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Transform;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;

/**
 *
 * @author codex
 */
public class VolumeContactSpace extends AbstractGameSystem {

    private EntityData ed;
    private EntitySet entities;
    private final Transform tempTransform = new Transform();
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class, true);
        entities = ed.getEntities(VolumeContactShape.class);
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    
    public VolumeRaycastResult raycast(Ray ray, CollisionResults results, EntityId user, ShapeFilter filter) {
        if (entities == null) {
            return null;
        }
        entities.applyChanges();
        if (results == null) {
            results = new CollisionResults();
        }
        CollisionResult closest = null;
        Entity closestEntity = null;
        for (var e : entities) {
            if (filter.filter(ed, user, e.getId())) {
                GameUtils.getWorldTransform(ed, e.getId(), tempTransform);
                var volume = createVolume(tempTransform, e.get(VolumeContactShape.class));
                if (volume.collideWith(ray, results) > 0) {
                    var res = results.getCollisionDirect(results.size()-1);
                    if (closest == null || res.getDistance() < closest.getDistance()) {
                        closest = res;
                        closestEntity = e;
                    }
                }
            }
        }
        if (closestEntity != null && closest != null) {
            return new VolumeRaycastResult(closestEntity.getId(), closest);
        } else {
            return null;
        }
    }
    
    private BoundingVolume createVolume(Transform transform, VolumeContactShape vol) {
        switch (vol.getType()) {
            case VolumeContactShape.SPHERE -> {
                var sphere = new BoundingSphere();
                sphere.setCenter(transform.getTranslation());
                sphere.setRadius(vol.getSize());
                return sphere;
            }
            case VolumeContactShape.BOX -> {
                var box = new BoundingBox();
                box.setCenter(transform.getTranslation());
                box.setXExtent(vol.getSize());
                box.setYExtent(vol.getSize());
                box.setZExtent(vol.getSize());
                return box;
            }
            default -> throw new IllegalArgumentException("Volume contact shape type does not exist.");
        }
    }
    
}
