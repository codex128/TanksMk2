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
public class FaceActiveCamera implements EntityComponent {
    
    private final Vector3f axis;
    
    public FaceActiveCamera() {
        this(null);
    }
    public FaceActiveCamera(Vector3f axis) {
        this.axis = axis;
    }
    
    public Vector3f getAxis() {
        return axis;
    }
    public boolean isAxisConstrained() {
        return axis != null;
    }
    
}
