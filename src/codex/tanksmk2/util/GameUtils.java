/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.util;

import codex.tanksmk2.components.EntityTransform;
import codex.tanksmk2.components.GameObject;
import codex.tanksmk2.components.Parent;
import com.jme3.collision.CollisionResults;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Transform;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.lemur.Axis;
import java.util.LinkedList;

/**
 *
 * @author codex
 */
public class GameUtils {
    
    private static Geometry planeGeometry;
    
    public static float applyDeadzone(float value, float deadzone) {
        if (value > deadzone) return value;
        else return deadzone;
    }
    public static Vector3f toVector3f(Vector2f vec, Axis normal, float normalValue) {
        return switch (normal) {
            case X -> new Vector3f(normalValue, vec.y, vec.x);
            case Y -> new Vector3f(vec.x, normalValue, vec.y);
            case Z -> new Vector3f(vec.x, vec.y, normalValue);
        };
    }
    public static Vector3f insertParameter(Vector3f vector, Axis parameter, float value) {
        return switch (parameter) {
            case X -> new Vector3f(value, vector.y, vector.z);
            case Y -> new Vector3f(vector.x, value, vector.z);
            case Z -> new Vector3f(vector.x, vector.y, value);
        };
    }
    
    /**
     * Creates a picking ray based on camera position and cursor location on screen.
     * @param cam
     * @param cursor
     * @return ray for cursor picking, or null if cursor is outside the camera viewport.
     * @author Paul Speed @pspeed42
     */
    public static Ray getCursorPickRay( Camera cam, Vector2f cursor ) {      
        if(!viewContainsCursor(cam, cursor)) return null;
        Vector3f clickNear = cam.getWorldCoordinates(cursor, 0);
        Vector3f clickDir = cam.getWorldCoordinates(cursor, 1)
                .subtractLocal(clickNear).normalizeLocal();
        return new Ray(clickNear, clickDir);
    }
    /**
     * Returns true if the cursor is within the camera viewport.
     * @param cam
     * @param cursor
     * @return 
     * @author Paul Speed @pspeed42
     */
    public static boolean viewContainsCursor( Camera cam, Vector2f cursor ) {
        float x1 = cam.getViewPortLeft();
        float x2 = cam.getViewPortRight();
        float y1 = cam.getViewPortBottom();
        float y2 = cam.getViewPortTop();
        if( x1 == 0 && x2 == 1 && y1 == 0 && y2 == 1 ) {
            return true;
        }
        float x = cursor.x / cam.getWidth();
        float y = cursor.y / cam.getHeight();
        return !(x < x1 || x > x2 || y < y1 || y > y2);
    }
    
    public static void collideRayToXZPlane(Ray ray, Vector3f offset, CollisionResults results) {
        if (planeGeometry == null) initPickPlaneGeometry();
        planeGeometry.setLocalTranslation(offset.subtract(500f, 0f, 500f));
        planeGeometry.collideWith(ray, results);
    }
    private static void initPickPlaneGeometry() {
        planeGeometry = new Geometry("planeGeometry", new Quad(1000f, 1000f));
        planeGeometry.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_X));
    }
    
    public static Transform getWorldTransform(EntityData ed, EntityId id) {
        var transforms = new LinkedList<EntityTransform>();
        while (id != null) {
            if (ed.getComponent(id, EntityTransform.class) != null) {
                transforms.addFirst(ed.getComponent(id, EntityTransform.class));
            }
            var p = ed.getComponent(id, Parent.class);
            if (p == null) break;
            id = p.getId();
        }
        Transform world = new Transform();
        for (var t : transforms) {
            world.getTranslation().addLocal(world.getRotation().mult(t.getTranslation()));
            world.getRotation().multLocal(t.getRotation());
        }
        return world;
    }
    public static Transform getWorldTransform(EntityData ed, Entity entity) {
        var transforms = new LinkedList<EntityTransform>();
        transforms.add(entity.get(EntityTransform.class));
        var parent = ed.getComponent(entity.getId(), Parent.class);
        while (parent != null) {
            if (ed.getComponent(parent.getId(), EntityTransform.class) != null) {
                transforms.addFirst(ed.getComponent(parent.getId(), EntityTransform.class));
            }
            parent = ed.getComponent(parent.getId(), Parent.class);
        }
        Transform world = new Transform();
        for (var t : transforms) {
            world.getTranslation().addLocal(world.getRotation().mult(t.getTranslation()));
            world.getRotation().multLocal(t.getRotation());
        }
        return world;
    }
    
    /**
     * Tests if the entity exists based on if it has a {@link GameObject} component.
     * @param ed
     * @param id
     * @return 
     */
    public static boolean entityExists(EntityData ed, EntityId id) {
        return ed.getComponent(id, GameObject.class) != null;
    }
    
}
