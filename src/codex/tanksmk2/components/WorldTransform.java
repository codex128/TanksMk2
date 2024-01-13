/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class WorldTransform implements EntityComponent {
    
    public static final WorldTransform INIT = new WorldTransform();
    
    private final Vector3f translation = new Vector3f();
    private final Quaternion rotation = new Quaternion();
    
    public WorldTransform() {
        this(Vector3f.ZERO, Quaternion.IDENTITY);
    }
    public WorldTransform(Transform t) {
        this(t.getTranslation(), t.getRotation());
    }
    public WorldTransform(Vector3f translation, Quaternion rotation) {
        this.translation.set(translation);
        this.rotation.set(rotation);
    }

    public Vector3f getTranslation() {
        return translation;
    }
    public Quaternion getRotation() {
        return rotation;
    }
    
}
