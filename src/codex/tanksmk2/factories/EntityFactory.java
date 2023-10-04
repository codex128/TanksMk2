/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import codex.tanksmk2.components.*;
import com.jme3.math.Vector3f;
import com.simsilica.bullet.Impulse;
import com.simsilica.bullet.ShapeInfo;
import com.simsilica.bullet.SpawnPosition;
import com.simsilica.es.EntityData;
import com.simsilica.es.common.Decay;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class EntityFactory {            
    
    private final EntityData ed;
    
    public EntityFactory(EntityData ed) {
        this.ed = ed;
    }
    
    public void createBullet(Vector3f spawn, Vector3f direction, float speed) {
        var bullet = ed.createEntity();
        ed.setComponents(bullet,
                new GameObject("bullet"),
                ModelInfo.create("bullet", ed),
                new Position(spawn),
                new Rotation(),
                new Direction(direction),
                new Speed(speed));
    }
    public void createMissile(Vector3f spawn, Vector3f direction, float speed) {
        var missile = ed.createEntity();
        ed.setComponents(missile,
                new GameObject("bullet"),
                ModelInfo.create("missile", ed),
                new Position(spawn),
                new Rotation(),
                new Direction(direction),
                new Speed(speed));
    }
    public void createGrenade(Vector3f spawn, Vector3f direction, float speed, SimTime time, float delay) {
        var grenade = ed.createEntity();
        ed.setComponents(grenade,
                new GameObject("grenade"),
                ModelInfo.create("grenade", ed),
                ShapeInfo.create("grenade", ed),
                new SpawnPosition(spawn),
                new Impulse(direction.mult(speed)),
                new Decay(time.getTime(), time.getFutureTime(delay)));
    }
    
    public void createExplosion(Vector3f position, float radius) {
        
    }
    
}
