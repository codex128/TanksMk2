/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.bots;

import codex.tanksmk2.components.BotUser;
import com.jme3.bullet.PhysicsSpace;
import com.simsilica.es.Entity;
import java.util.HashMap;

/**
 *
 * @author codex
 */
public abstract class Bot {
    
    protected Entity[] users = new Entity[BotUser.EXTENT];
    private int numUsers = 0;
    
    public abstract void preUpdate(BotUpdate update);
    
    public abstract void move(BotUpdate update);
    
    public abstract void aim(BotUpdate update);
    
    public abstract void shoot(BotUpdate update);
    
    public abstract void postUpdate(BotUpdate update);
    
    public abstract void physicsUpdate(PhysicsSpace space, float timeStep, BotUpdate update);
    
    public void addUser(Entity e) {
        for (int j : e.get(BotUser.class).getElements()) {
            users[j] = e;
            numUsers++;
        }
    }
    
    public void removeUser(Entity e) {
        for (int j : e.get(BotUser.class).getElements()) {
            users[j] = null;
            numUsers--;
        }
    }
    
    public int getNumUsers() {
        return numUsers;
    }
    
    public boolean readyForUpdate() {
        return users[BotUser.MAIN] != null;
    }
    
    public Entity[] getUsers() {
        return users;
    }
    
    public Entity getUser(int i) {
        return users[i];
    }
    
    public boolean hasUser(int i) {
        return users[i] != null;
    }
    
    public boolean hasUsers(int... indices) {
        for (int i : indices) {
            if (users[i] == null) return false;
        }
        return true;
    }
    
}
