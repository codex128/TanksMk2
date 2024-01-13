/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.bots;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class BotUpdate {
    
    public final EntityData ed;
    public final EntitySet targets;
    public Entity entity;
    public SimTime time;
    public float difficulty;
    public float pressure;
    
    public BotUpdate(EntityData ed, EntitySet targets) {
        this.ed = ed;
        this.targets = targets;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
    
    public void setTime(SimTime time) {
        this.time = time;
    }
    
    public void setDifficulty(float difficulty) {
        this.difficulty = difficulty;
    }
    
    public void setPressure(float pressure) {
        this.pressure = pressure;
    }
    
}
