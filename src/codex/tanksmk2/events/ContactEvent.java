/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.events;

import com.jme3.collision.CollisionResult;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class ContactEvent {
    
    public final EntityId bullet, target;
    public final CollisionResult collision;

    public ContactEvent(EntityId bullet, EntityId target, CollisionResult collision) {
        this.bullet = bullet;
        this.target = target;
        this.collision = collision;
    }
    
    /**
     * The entity representing the bullet.
     * @return 
     */
    public EntityId getBullet() {
        return bullet;
    }
    /**
     * The entity representing the target.
     * <p>This entity also holds the contact responses.
     * @return 
     */
    public EntityId getTarget() {
        return target;
    }
    /**
     * The collision result data.
     * @return 
     */
    public CollisionResult getCollision() {
        return collision;
    }
    
}
