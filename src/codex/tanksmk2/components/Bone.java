/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

/**
 * Indicates that this spatial controls an armature bone.
 * 
 * @author codex
 */
public class Bone implements EntityComponent {
    
    private EntityId model;
    private String bone;

    public Bone(EntityId model, String bone) {
        this.model = model;
        this.bone = bone;
    }

    public EntityId getModel() {
        return model;
    }
    public String getBone() {
        return bone;
    }
    @Override
    public String toString() {
        return "Bone{" + "model=" + model + ", bone=" + bone + '}';
    }
    
}
