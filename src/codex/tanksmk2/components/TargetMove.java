/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

/**
 * Target move direction.
 * 
 * @author codex
 */
public class TargetMove implements EntityComponent {
    
    private final Vector3f direction = new Vector3f();
    
    public TargetMove() {}
    public TargetMove(Vector3f direction) {
        this.direction.set(direction).normalizeLocal();
    }

    public Vector3f getDirection() {
        return direction;
    }
    @Override
    public String toString() {
        return "TargetDirection{" + "direction=" + direction + '}';
    }
    
}
