/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.collision;

import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import java.util.Objects;

/**
 *
 * @author codex
 */
public class Segment {
    
    private final Vector3f start = new Vector3f();
    private final Vector3f end = new Vector3f();
    
    public Segment() {}
    public Segment(Vector3f start, Vector3f end) {
        set(start, end);
    }
    public Segment(Vector3f origin, Vector3f direction, float distance) {
        set(origin, direction, distance);
    }
    public Segment(Ray ray, float distance) {
        set(ray.origin, ray.direction, distance);
    }
    
    public final Segment set(Vector3f start, Vector3f end) {
        this.start.set(start);
        this.end.set(end);
        return this;
    }
    public final Segment set(Vector3f origin, Vector3f direction, float distance) {
        this.start.set(origin);
        this.end.set(direction).multLocal(distance).addLocal(origin);
        return this;
    }
    public Segment set(Ray ray, float distance) {
        return set(ray.origin, ray.direction, distance);
    }
    public Segment set(Segment segment) {
        return set(segment.start, segment.end);
    }
    
    public Segment setDirection(Vector3f direction) {
        return set(start, direction, getDistance());
    }
    public Segment setDistance(float distance) {
        return set(start, getDirection(end), distance);
    }
    
    public Segment flipLocal() {
        float temp = start.x;
        start.x = end.x;
        end.x = temp;
        temp = start.y;
        start.y = end.y;
        end.y = temp;
        temp = start.z;
        start.z = end.z;
        end.z = temp;
        return this;
    }
    public Segment flip(Segment store) {
        if (store == null) {
            store = new Segment();
        }
        return store.set(this).flipLocal();
    }
    
    public Vector3f getStart() {
        return start;
    }
    public Vector3f getEnd() {
        return end;
    }    
    public Vector3f getDirection(Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        return store.set(end).subtractLocal(start).normalizeLocal();
    }
    public float getDistance() {
        return start.distance(end);
    }
    public float getDistanceSq() {
        return start.distanceSquared(end);
    }
    
    public Ray toRay(Ray store) {
        if (store == null) {
            store = new Ray();
        }
        store.setOrigin(start);
        getDirection(store.direction);
        return store;
    }
    
    @Override
    @SuppressWarnings({"CloneDeclaresCloneNotSupported", "CloneDoesntCallSuperClone"})
    public Segment clone() {
        return new Segment(start, end);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + start.hashCode();
        hash = 79 * hash + end.hashCode();
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Segment other = (Segment)obj;
        return Objects.equals(this.start, other.start) && Objects.equals(this.end, other.end);
    }
    
}
