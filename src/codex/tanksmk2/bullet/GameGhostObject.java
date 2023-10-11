/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.bullet;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.simsilica.bullet.EntityGhostObject;
import com.simsilica.bullet.EntityRigidBody;
import com.simsilica.bullet.SpawnPosition;
import com.simsilica.es.EntityId;

/**
 * Extension of {@link EntityGhostObject} to provide access to
 * protected methods for {@link GameBulletSystem}.
 * 
 * @author codex
 */
public class GameGhostObject extends EntityGhostObject {
    
    public GameGhostObject(EntityId id, CollisionShape shape, byte collisionMask) {
        super(id, shape, collisionMask);
    }
    
    @Override
    public void setParent(EntityRigidBody parent) {
        super.setParent(parent);
    }
    @Override
    public void setParent( EntityId parentId, EntityRigidBody parent, SpawnPosition parentOffset ) {
        super.setParent(parentId, parent, parentOffset);
    }
    @Override
    public boolean updateToParent() {
        return super.updateToParent();
    }
    
}
