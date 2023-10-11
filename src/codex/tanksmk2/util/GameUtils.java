/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.util;

import codex.tanksmk2.bullet.GeometricShape;
import codex.tanksmk2.components.*;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Ray;
import com.jme3.math.Transform;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.common.Decay;
import com.simsilica.lemur.Axis;
import com.simsilica.sim.SimTime;
import java.util.LinkedList;

/**
 *
 * @author codex
 */
public class GameUtils {
    
    public static float applyDeadzone(float value, float deadzone) {
        if (value > deadzone) return value;
        else return deadzone;
    }
    public static float clamp(float value, Vector2f range) {
        if (value < range.x) return range.x;
        if (value > range.y) return range.y;
        return value;
    }    
    public static double getSecondsSince(SimTime time, double point) {
        return time.getTimeInSeconds()-point;
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
    public static Vector3f ricochet(Vector3f vector, Vector3f normal) {
        return normal.mult(normal.dot(vector)*-2).addLocal(vector).normalizeLocal();
    }
    public static Vector3f asVelocity(Entity entity) {
        return new Vector3f(entity.get(Direction.class).getDirection()).multLocal(entity.get(Speed.class).getSpeed());
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
        return new Ray(clickNear, cam.getWorldCoordinates(cursor, 1).subtractLocal(clickNear).normalizeLocal());
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
    
    public static Transform getWorldTransform(EntityData ed, EntityId id) {
        var transforms = new LinkedList<Transform>();
        while (id != null) {
            var position = ed.getComponent(id, Position.class);
            var rotation = ed.getComponent(id, Rotation.class);
            if (position != null || rotation != null) {
                transforms.addFirst(new Transform());
                if (position != null) {
                    transforms.getFirst().setTranslation(position.getPosition());
                }
                if (rotation != null) {
                    transforms.getFirst().setRotation(rotation.getRotation());
                }
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
        // Originally, I did a slightly different operation for this method,
        // but stuff changed and I didn't feel like maintaining an extra, almost
        // identical method. So, here is an almost useless method.
        return getWorldTransform(ed, entity.getId());
    }
    public static boolean isDefunct(EntityData ed, EntityId id) {
        while (true) {
            if (ed.getComponent(id, Dead.class) != null) {
                return true;
            }
            var parent = ed.getComponent(id, Parent.class);
            if (parent == null) {
                return false;
            }
            id = parent.getId();
        }
    }
    
    /**
     * Tests if the entity exists based on if it has a {@link GameObject} component.
     * <p>
     * For this reason, all entities are expected to have a {@link GameObject} component.
     * @param ed
     * @param id
     * @return 
     */
    public static boolean entityExists(EntityData ed, EntityId id) {
        return ed.getComponent(id, GameObject.class) != null;
    }    
    public static <T extends EntityComponent> T getComponent(EntityData ed, EntityId id, Class<T> type) {
        while (id != null) {
            T component = ed.getComponent(id, type);
            if (component != null) {
                return component;
            }
            var parent = ed.getComponent(id, Parent.class);
            if (parent == null) {
                return null;
            }
            id = parent.getId();
        }
        return null;
    }
    
    public static CollisionShape createGeometricCollisionShape(GeometricShape shape, Spatial spatial) {
        return switch (shape) {
            case Box         -> CollisionShapeFactory.createBoxShape(spatial);
            case DynamicMesh -> CollisionShapeFactory.createDynamicMeshShape(spatial);
            case GImpact     -> CollisionShapeFactory.createGImpactShape(spatial);
            case MergedBox   -> CollisionShapeFactory.createMergedBoxShape(spatial);
            case MergedHull  -> CollisionShapeFactory.createMergedHullShape(spatial);
            case MergedMesh  -> CollisionShapeFactory.createMergedMeshShape(spatial);
            case Mesh        -> CollisionShapeFactory.createMeshShape(spatial);
            //case VHACD     -> CollisionShapeFactory.createVhacdShape(spatial, parameters, new CompoundCollisionShape());
            //case VHACD4    -> CollisionShapeFactory.createVhacdShape(spatial, parameters, new CompoundCollisionShape());
            case Vhacd  -> throw new UnsupportedOperationException("VHACD collision shapes are not supported yet!");
            case Vhacd4 -> throw new UnsupportedOperationException("VHACD collision shapes are not supported yet!");
        };
    }
    
    public static void appendId(EntityId id, Spatial spatial) {
        spatial.setUserData(EntityId.class.getName(), id.getId());
    }
    public static EntityId fetchId(Spatial spatial, int depth) {
        while (spatial != null) {
            Long id = spatial.getUserData(EntityId.class.getName());
            if (id != null) return new EntityId(id);
            if (depth-- == 0) return null;
            spatial = spatial.getParent();
        }
        return null;
    }
    
    public static Decay duration(SimTime time, double seconds) {
        return Decay.duration(time.getFrame(), time.toSimTime(seconds));
    }
    
}
