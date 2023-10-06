/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.input;

import codex.tanksmk2.components.LookAt;
import codex.tanksmk2.util.GameUtils;
import com.jme3.input.InputManager;
import com.jme3.math.Plane;
import com.jme3.math.Ray;
import com.jme3.math.Transform;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;

/**
 * Publishes mouse direction by intersecting a pick ray with a plane.
 * 
 * @author codex
 */
public class MouseDirectionPublisher implements PlayerInputPublisher {
    
    private final EntityData ed;
    private final InputManager inputManager;
    private final Entity entity;
    private final Camera camera;
    private final TankInputFunctions functions;
    private Ray pickRay;
    private final Transform lastCamTransform = new Transform();
    private final Vector2f lastCursorPosition = new Vector2f();

    public MouseDirectionPublisher(EntityData ed, InputManager inputManager, Entity entity,
            TankInputFunctions functions, Camera camera) {
        this.ed = ed;
        this.inputManager = inputManager;
        this.entity = entity;
        this.functions = functions;
        this.camera = camera;
    }
    
    @Override
    public void onEnable() {}
    @Override
    public void onDisable() {}
    @Override
    public void update(float tpf) {
        updatePickRay();
        var transform = GameUtils.getWorldTransform(ed, entity.getId());
        var intersection = new Vector3f();
        if (pickRay.intersectsWherePlane(new Plane(Vector3f.UNIT_Y, transform.getTranslation()), intersection)) {
            ed.setComponent(entity.getId(), new LookAt(intersection.subtractLocal(transform.getTranslation()).normalizeLocal()));
        }
    }
    @Override
    public Functions getFunctions() {
        return functions;
    }
    
    private boolean updatePickRay() {
        if (parametersChanged()) {
            pickRay = GameUtils.getCursorPickRay(camera, inputManager.getCursorPosition());
            lastCursorPosition.set(inputManager.getCursorPosition());
            lastCamTransform.setTranslation(camera.getLocation());
            lastCamTransform.setRotation(camera.getRotation());
            return true;
        }
        return false;
    }
    private boolean parametersChanged() {
        return pickRay == null
                || !inputManager.getCursorPosition().equals(lastCursorPosition)
                || !camera.getLocation().equals(lastCamTransform.getTranslation())
                || !camera.getRotation().equals(lastCamTransform.getRotation());
    }
    
}
