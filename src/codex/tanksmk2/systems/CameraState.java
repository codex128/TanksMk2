/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.ESAppState;
import codex.tanksmk2.components.CameraId;
import codex.tanksmk2.components.CameraPriority;
import codex.tanksmk2.components.EntityTransform;
import com.jme3.app.Application;
import com.jme3.renderer.Camera;
import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;
import java.util.HashMap;

/**
 *
 * @author codex
 */
public class CameraState extends ESAppState {
    
    public static final CameraId APP_CAMERA = CameraId.create();
    
    private EntitySet entities;
    private HashMap<CameraId, CameraHolder> cameras = new HashMap<>();
    
    @Override
    protected void init(Application app) {
        entities = ed.getEntities(CameraId.class, EntityTransform.class, CameraPriority.class);
        cameras.put(APP_CAMERA, new CameraHolder(cam));
    }
    @Override
    protected void cleanup(Application app) {
        entities.release();
    }
    @Override
    protected void onEnable() {}
    @Override
    protected void onDisable() {}    
    @Override
    public void update(float tpf) {
        entities.applyChanges();
        //if (entities.applyChanges()) {
            resetHolderAccess();
            for (var e : entities) {
                updateHolderAccess(e);
            }
            //for (var e : entities.getChangedEntities()) {
            //    updateHolderAccess(e);
            //}
            updateCameraTransforms();
        //}
    }
    
    private void resetHolderAccess() {
        for (var h : cameras.values()) {
            h.accessLevel = CameraPriority.NO_PRIORITY;
            h.entity = null;
        }
    }
    private void updateHolderAccess(Entity e) {
        var holder = cameras.get(e.get(CameraId.class));
        var priority = e.get(CameraPriority.class);
        if (holder != null && holder.accessLevel.getLevel() < priority.getLevel()) {
            holder.accessLevel = priority;
            holder.entity = e;
        }
    }
    private void updateCameraTransforms() {
        System.out.println("updating camera transforms");
        for (var h : cameras.values()) if (h.entity != null) {
            var transform = h.entity.get(EntityTransform.class);
            h.camera.setLocation(transform.getTranslation());
            h.camera.setRotation(transform.getRotation());
            System.out.println("camera set to holder transform");
        }
    }
    
    private class CameraHolder {
        
        Camera camera;
        CameraPriority accessLevel;
        Entity entity;
        
        CameraHolder(Camera camera) {
            this(camera, CameraPriority.NO_PRIORITY);
        }
        CameraHolder(Camera camera, CameraPriority accessLevel) {
            this.camera = camera;
            this.accessLevel = accessLevel;
        }
        
    }
    
}
