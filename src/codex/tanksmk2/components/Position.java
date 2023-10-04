/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class Position implements EntityComponent {
    
    private final Vector3f position = new Vector3f();

    public Position() {}
    public Position(Vector3f position) {
        this.position.set(position);
    }
    public Position(float x, float y, float z) {
        position.set(x, y, z);
    }
    public Position(Vector2f position, float zIndex) {
        this.position.set(position.x, position.y, zIndex);
    }

    public Vector3f getPosition() {
        return position;
    }
    @Override
    public String toString() {
        return "Position{" + position + '}';
    }
    
}
