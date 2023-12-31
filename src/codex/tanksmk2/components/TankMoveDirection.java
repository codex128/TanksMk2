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
public class TankMoveDirection implements EntityComponent {
    
    private final Vector3f direction = new Vector3f();
    
    public TankMoveDirection(Vector3f direction) {
        this.direction.set(direction);
    }
    
    public Vector3f getDirection() {
        return direction;
    }
    public boolean isMoving() {
        return !direction.equals(Vector3f.ZERO);
    }
    @Override
    public String toString() {
        return "TankMovement{" + "movement=" + direction + '}';
    }
    
}
