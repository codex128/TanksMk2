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
public class LookAt implements EntityComponent {
    
    private final Vector3f vector = new Vector3f();
    private final boolean local;
    
    public LookAt(Vector3f direction) {
        this(direction, true);
    }
    public LookAt(Vector3f vector, boolean local) {
        this.vector.set(vector);
        this.local = local;
    }

    public Vector3f getVector() {
        return vector;
    }
    public boolean isLocal() {
        return local;
    }
    @Override
    public String toString() {
        return "AimDirection{" + "vector=" + vector + '}';
    }
    
}
