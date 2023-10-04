/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.jme3.math.Quaternion;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class FaceVelocity implements EntityComponent {
    
    private final Quaternion offset;

    public FaceVelocity() {
        offset = null;
    }
    public FaceVelocity(Quaternion offset) {
        this.offset = new Quaternion(offset);
    }

    public Quaternion getOffset() {
        return offset;
    }
    public boolean hasRotationOffset() {
        return offset != null;
    }
    @Override
    public String toString() {
        return "FaceVelocity{" + "offset=" + offset + '}';
    }
    
}
