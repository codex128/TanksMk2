/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.simsilica.es.EntityData;
import com.simsilica.sim.GameSystemManager;
import com.simsilica.sim.SimTime;
import com.simsilica.state.GameSystemsState;

/**
 * Commonly used info in entity manufacturing.
 * 
 * @author codex
 */
public class FactoryInfo {
    
    public final String name;
    public final EntityData ed;
    public final SimTime time;

    public FactoryInfo(String name, EntityData ed, SimTime time) {
        this.name = name;
        this.ed = ed;
        this.time = time;
    }
    public FactoryInfo(String name, GameSystemManager manager) {
        this(name, manager.get(EntityData.class), manager.getStepTime());
    }
    public FactoryInfo(String name, GameSystemsState state) {
        this(name, state.get(EntityData.class), state.getStepTime());
    }
    public FactoryInfo(String name, EntityData ed, AppStateManager manager) {
        this(name, ed, manager.getState(GameSystemsState.class).getStepTime());
    }
    public FactoryInfo(String name, EntityData ed, Application app) {
        this(name, ed, app.getStateManager());
    }

    public String getName() {
        return name;
    }
    public EntityData getEntityData() {
        return ed;
    }
    public SimTime getTime() {
        return time;
    }
    
    public static FactoryInfo create(String name, GameSystemManager manager) {
        return new FactoryInfo(name, manager.get(EntityData.class), manager.getStepTime());
    }
    
}
