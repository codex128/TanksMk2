/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import codex.tanksmk2.components.Stats;

/**
 * Contains basic stats for different types of bullets.
 * 
 * @author codex
 */
public class BulletStats {
    
    public static final BulletStats
            BULLET = new BulletStats(10, 1),
            MISSILE = new BulletStats(25, 0),
            GRENADE = new BulletStats(5, 0);
    
    private float speed;
    private int bounces;
    
    public BulletStats(float speed, int bounces) {
        this.speed = speed;
        this.bounces = bounces;
    }

    public float getSpeed() {
        return speed;
    }
    public int getBounces() {
        return bounces;
    }
    
    public BulletStats apply(Stats stats) {
        return new BulletStats(speed, bounces).applyLocal(stats);
    }
    public BulletStats applyLocal(Stats stats) {
        speed += stats.get(Stats.BULLET_SPEED);
        bounces += (int)stats.get(Stats.BOUNCES);
        return this;
    }
    
}
