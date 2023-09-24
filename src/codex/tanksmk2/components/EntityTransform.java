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
public class EntityTransform implements EntityComponent {
    
    private final Vector3f translation = new Vector3f();
    private final Quaternion rotation = new Quaternion();

    public EntityTransform(Vector3f translation, Quaternion rotation) {
        this.translation.set(translation);
        this.rotation.set(rotation);
    }
    public EntityTransform(EntityTransform transform) {
        translation.set(transform.translation);
        rotation.set(transform.rotation);
    }
    public EntityTransform(Transform transform) {
        translation.set(transform.getTranslation());
        rotation.set(transform.getRotation());
    }

    public Vector3f getTranslation() {
        return translation;
    }
    public Quaternion getRotation() {
        return rotation;
    }    
    public Transform toJmeTransform() {
        return new Transform(translation, rotation, Vector3f.UNIT_XYZ);
    }
    @Override
    public String toString() {
        return "EntityTransform{" + "translation=" + translation + ", rotation=" + rotation + '}';
    }
    
    public EntityTransform setTranslation(Vector3f translation) {
        return new EntityTransform(translation, rotation);
    }
    public EntityTransform setRotation(Quaternion rotation) {
        return new EntityTransform(translation, rotation);
    }
    public EntityTransform setRotation(Vector3f lookAtDir, Vector3f up) {
        return setRotation(new Quaternion().lookAt(lookAtDir, up));
    }
    public EntityTransform setRotation(float angle, Vector3f axis) {
        return setRotation(new Quaternion().fromAngleAxis(angle, axis));
    }
    public EntityTransform setRotation(float angleX, float angleY, float angleZ) {
        return setRotation(new Quaternion().fromAngles(angleX, angleY, angleZ));
    }
    public EntityTransform addRotation(Quaternion rotation) {
        return new EntityTransform(translation, this.rotation.mult(rotation));
    }
    
}
