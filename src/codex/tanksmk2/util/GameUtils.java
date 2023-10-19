/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.util;

import codex.tanksmk2.bullet.GeometricShape;
import codex.tanksmk2.components.*;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Consumer;

/**
 * Useful utility methods for general game purposes.
 * 
 * @author codex
 */
public class GameUtils {
    
    /**
     * Returns the number of seconds since the reference point.
     * @param time current application time
     * @param point reference point (in seconds)
     * @return number of seconds since the reference point
     */
    public static double getSecondsSince(SimTime time, double point) {
        return time.getTimeInSeconds()-point;
    }
    
    /**
     * Converts {@link Vector2f} to {@link Vector3f}.
     * <p>
     * The normal axis indicates how the {@code Vector2f} components
     * are distributed to the {@code Vector3f}.
     * <ul>
     *   <li>Axis.X = normal, x, y
     *   <li>Axis.Y = x, normal, y
     *   <li>Axis.Z = x, y, normal
     * </ul>
     * 
     * @param vec Vector2f to convert
     * @param normal the axis defining how the components are distributed
     * @param normalValue the "odd" component value
     * @return new Vector3f instance
     */
    public static Vector3f toVector3f(Vector2f vec, Axis normal, float normalValue) {
        return switch (normal) {
            case X -> new Vector3f(normalValue, vec.y, vec.x);
            case Y -> new Vector3f(vec.x, normalValue, vec.y);
            case Z -> new Vector3f(vec.x, vec.y, normalValue);
        };
    }
    
    /**
     * Sets the vector component and returns a new instance.
     * <p>
     * The parameter axis defines which component should be set.
     * 
     * @param vector vector to set
     * @param parameter vector component to set
     * @param value setter value
     * @return new vector instance
     */
    public static Vector3f insertParameter(Vector3f vector, Axis parameter, float value) {
        return switch (parameter) {
            case X -> new Vector3f(value, vector.y, vector.z);
            case Y -> new Vector3f(vector.x, value, vector.z);
            case Z -> new Vector3f(vector.x, vector.y, value);
        };
    }
    
    /**
     * Reflects the direction vector off a plane defined by the normal.
     * 
     * @param vector direction
     * @param normal normal of the reflecting plane
     * @return ricocheted vector
     */
    public static Vector3f ricochet(Vector3f vector, Vector3f normal) {
        return normal.mult(normal.dot(vector)*-2).addLocal(vector).normalizeLocal();
    }
    
    /**
     * Converts {@link Direction} and {@link Speed} components to a velocity vector.
     * 
     * @param entity entity containing direction and speed components
     * @return velocity
     */
    public static Vector3f asVelocity(Entity entity) {
        return new Vector3f(entity.get(Direction.class).getDirection()).multLocal(entity.get(Speed.class).getSpeed());
    }
    
    /**
     * Creates a picking ray based on camera position and cursor location on screen.
     * 
     * @param cam camera to calculate the ray from
     * @param cursor the cursor location on screen
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
     * 
     * @param cam camera to calculate from
     * @param cursor cursor location on screen
     * @return true if cursor is within camera viewport
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
    
    /**
     * Calculates the world transform of an entity.
     * 
     * @param ed entity data
     * @param id id of the entity
     * @return world transform
     */
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
            //world.getScale().multLocal(t.getScale());
        }
        return world;
    }
    
    /**
     * Calculates the world transform of an entity's parent.
     * 
     * @param ed entity data
     * @param id id of the entity
     * @return 
     */
    public static Transform getParentWorldTransform(EntityData ed, EntityId id) {
        var parent = ed.getComponent(id, Parent.class);
        if (parent != null) {
            return getWorldTransform(ed, parent.getId());
        }
        else {
            return Transform.IDENTITY;
        }
    }
    
    /**
     * Returns true if the entity is dead.
     * <p>
     * An entity is dead when it contains a {@link Health} component
     * that is {@link Health#isExhausted()}.
     * 
     * @param ed entity data
     * @param id id of the entity
     * @return true if the entity is dead
     */
    public static boolean isDead(EntityData ed, EntityId id) {
        while (true) {
            var health = ed.getComponent(id, Health.class);
            if (health != null) {
                return health.isExhausted();
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
    
    /**
     * Fetches the component from the entity or parent entities.
     * 
     * @param <T>
     * @param ed
     * @param id
     * @param type
     * @return 
     */
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
    
    /**
     * Traverses the entity hierarchy through {@link Parent} components.
     * <p>
     * This traversal starts at the lowest entity, and works its
     * way up, so that it traverses every parent of the root entity.
     * 
     * @param ed entity data
     * @param root the entity to start at
     * @param foreach consumer to be called for each step
     */
    public static void traverseEntityHierarchy(EntityData ed, EntityId root, Consumer<EntityId> foreach) {
        while (root != null) {
            foreach.accept(root);
            var parent = ed.getComponent(root, Parent.class);
            if (parent == null) break;
            root = parent.getId();
        }
    }
    
    /**
     * Get the {@link GameObject} component from this entity.
     * 
     * @param ed entity data
     * @param id id of the entity
     * @return 
     */
    public static GameObject getGameObject(EntityData ed, EntityId id) {
        return ed.getComponent(id, GameObject.class);
    }
    
    /**
     * Creates a collision shape based on the spatial shape.
     * 
     * @param shape shape generation method
     * @param spatial spatial to calculate from
     * @return physical collision shape
     */
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
    
    /**
     * Appends the entity's id to the userdata of the spatial.
     * <p>
     * The key of the userdata is "EntityId".
     * 
     * @param id id of the spatial, or null to clear the currently appended id.
     * @param spatial spatial to append to
     */
    public static void appendId(EntityId id, Spatial spatial) {
        spatial.setUserData(EntityId.class.getName(), id.getId());
    }
    
    /**
     * Fetches an appended {@link EntityId} from the spatial, if it exists.
     * 
     * @param spatial spatial to fetch from
     * @param depth amount the algorithm will travel up the entity hierarchy to find the id, or -1 for no constraint
     * @return appended entity id, or null if none is found
     */
    public static EntityId fetchId(Spatial spatial, int depth) {
        while (spatial != null) {
            Long id = spatial.getUserData(EntityId.class.getName());
            if (id != null) return new EntityId(id);
            if (depth-- == 0) return null;
            spatial = spatial.getParent();
        }
        return null;
    }
    
    /**
     * Creates a {@link Decay} component starting at the current time
     * and ending at a future time so many seconds away.
     * 
     * @param time current SimTime
     * @param seconds seconds the decay lasts after the current time
     * @return decay component
     */
    public static Decay duration(SimTime time, double seconds) {
        return new Decay(time.getFrame(), time.getFutureTime(seconds));
    }
    
    /**
     * Constructs a {@link ColorRGBA} from the String values in the list
     * starting at the index.
     * <p>
     * String values are parsed using {@link Float#parseFloat(java.lang.String)}, which
     * can throw an exception if the strings do not accurately represent float values.
     * 
     * @param list list of strings to calculate the color from
     * @param i starting index of the calculations
     * @return color
     */
    public static ColorRGBA colorArrayList(ArrayList<String> list, int i) {
        return color4(list.get(i), list.get(i+1), list.get(i+2), (i+3 < list.size() ? list.get(i+3) : "1"));
    }
    private static ColorRGBA color4(String r, String g, String b, String a) {
        return new ColorRGBA(Float.parseFloat(r), Float.parseFloat(g), Float.parseFloat(b), Float.parseFloat(a));
    }
    
    /**
     * Gets the component from the entity, if it exists. Otherwise, returns the
     * given default value.
     * 
     * @param <T> component type
     * @param ed entity data
     * @param id id of the entity
     * @param type class of the component to get
     * @param defComponent default component to return if no component was found
     * @return the component under the entity, or the default value if none was found
     */
    public static <T extends EntityComponent> T getComponent(EntityData ed, EntityId id, Class<T> type, T defComponent) {
        T c = ed.getComponent(id, type);
        if (c == null) return defComponent;
        else return c;
    }
    
    /**
     * Generates a random unit vector.
     * <p>
     * This method is not completely random. It is somewhat
     * biased toward the "corners."
     * 
     * @param store vector to write to, or null to use a new vector
     * @return random unit vector
     */
    public static Vector3f randomUnitVector(Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        return store.set(
            FastMath.rand.nextFloat(-1, 1),
            FastMath.rand.nextFloat(-1, 1),
            FastMath.rand.nextFloat(-1, 1)
        ).normalizeLocal();
    }
    
    /**
     * Returns a vector pointing from one vector to another.
     * 
     * @param here
     * @param there
     * @return 
     */
    public static Vector3f directionTo(Vector3f here, Vector3f there) {
        return there.subtract(here).normalizeLocal();
    }
    
    /**
     * Runs the {@link Consumer} if the component exists in the entity.
     * 
     * @param <T> component class type
     * @param ed entity data
     * @param id id of the entity
     * @param component class of the desired component
     * @param notify consumer
     */
    public static <T extends EntityComponent> void onComponentExists(EntityData ed, EntityId id, Class<T> component, Consumer<T> notify) {
        T c = ed.getComponent(id, component);
        if (c != null) {
            notify.accept(c);
        }
    }
    
}
