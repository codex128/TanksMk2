/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class Velocity implements EntityComponent {
    
    private final Vector3f direction = new Vector3f();
    private final float magnitude;

    public Velocity(Vector3f velocity) {
        direction.set(velocity).normalizeLocal();
        magnitude = velocity.length();
    }
    public Velocity(Vector3f direction, float magnitude) {
        this.direction.set(direction);
        this.magnitude = magnitude;
    }

    public Vector3f getVelocity() {
        return direction.mult(magnitude);
    }
    public Vector3f getVelocity(Vector3f store) {
        return direction.mult(magnitude, store);
    }
    public Vector3f getDirection() {
        return direction;
    }
    public Vector3f getDirection(Vector3f store) {
        return store.set(direction);
    }
    public float getMagnitude() {
        return magnitude;
    }
    @Override
    public String toString() {
        return "Velocity{" + "direction=" + direction + ", magnitude=" + magnitude + '}';
    }
    
    public Velocity setDirection(Vector3f direction) {
        return new Velocity(direction, magnitude);
    }
    public Velocity setMagnitude(float magnitude) {
        return new Velocity(direction, magnitude);
    }
    
}
