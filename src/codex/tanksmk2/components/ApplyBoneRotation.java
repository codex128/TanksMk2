/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class ApplyBoneRotation implements EntityComponent {
    
    public static final boolean ENTITY_TO_BONE = true, BONE_TO_ENTITY = false;
    
    private final boolean direction;

    public ApplyBoneRotation(boolean direction) {
        this.direction = direction;
    }

    public boolean isDirection() {
        return direction;
    }
    @Override
    public String toString() {
        return "ApplyBoneRotation{" + "direction=" + direction + '}';
    }
    
}
