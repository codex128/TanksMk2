/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.collision;

import com.jme3.math.Vector3f;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public interface RaycastResult {
    
    public EntityId getCollisionEntity();
    
    public Vector3f getCollisionPoint();
    
    public Vector3f getCollisionNormal();
    
    public float getCollisionDistance();
    
}
