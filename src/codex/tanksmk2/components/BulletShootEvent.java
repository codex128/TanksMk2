/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class BulletShootEvent implements EntityComponent {
    
    private final EntityId owner, gun, bullet;

    public BulletShootEvent(EntityId owner, EntityId gun, EntityId bullet) {
        this.owner = owner;
        this.gun = gun;
        this.bullet = bullet;
    }

    public EntityId getOwner() {
        return owner;
    }
    public EntityId getGun() {
        return gun;
    }
    public EntityId getBullet() {
        return bullet;
    }
    @Override
    public String toString() {
        return "BulletShootEvent{" + "owner=" + owner + ", gun=" + gun + ", bullet=" + bullet + '}';
    }
    
}
