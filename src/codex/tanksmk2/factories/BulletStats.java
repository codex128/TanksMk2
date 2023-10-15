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
            BULLET = new BulletStats(10, 1, 25),
            MISSILE = new BulletStats(25, 0, 50),
            GRENADE = new BulletStats(5, 0, 100);
    
    private float speed;
    private int bounces;
    private float damage;
    
    public BulletStats(float speed, int bounces, float damage) {
        this.speed = speed;
        this.bounces = bounces;
        this.damage = damage;
    }

    public float getSpeed() {
        return speed;
    }
    public int getBounces() {
        return bounces;
    }
    public float getDamage() {
        return damage;
    }
    
    public BulletStats apply(Stats stats) {
        return new BulletStats(speed, bounces, damage).applyLocal(stats);
    }
    public BulletStats applyLocal(Stats stats) {
        speed += stats.get(Stats.BULLET_SPEED);
        bounces += (int)stats.get(Stats.BOUNCES);
        damage += stats.get(Stats.DAMAGE);
        return this;
    }
    
}
