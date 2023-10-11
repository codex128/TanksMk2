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
public class AngularVelocity implements EntityComponent {
    
    private final Vector3f velocity = new Vector3f();
    
    public AngularVelocity(Vector3f velocity) {
        this.velocity.set(velocity);
    }

    public Vector3f getVelocity() {
        return velocity;
    }
    @Override
    public String toString() {
        return "AngularVelocity{" + "velocity=" + velocity + '}';
    }
    
}
