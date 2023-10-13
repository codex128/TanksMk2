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
    
    public String name;
    public EntityData ed;
    public SimTime time;

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
    public FactoryInfo(Prefab prefab, EntityData ed, SimTime time) {
        this(prefab.getName(ed), ed, time);
    }
    public FactoryInfo(Prefab prefab, GameSystemManager manager) {
        this(prefab, manager.get(EntityData.class), manager.getStepTime());
    }
    public FactoryInfo(Prefab prefab, GameSystemsState state) {
        this(prefab, state.get(EntityData.class), state.getStepTime());
    }
    public FactoryInfo(Prefab prefab, EntityData ed, AppStateManager manager) {
        this(prefab, ed, manager.getState(GameSystemsState.class).getStepTime());
    }
    public FactoryInfo(Prefab prefab, EntityData ed, Application app) {
        this(prefab, ed, app.getStateManager());
    }
    public FactoryInfo(EntityData ed, SimTime time) {
        this((String)null, ed, time);
    }
    public FactoryInfo(GameSystemManager manager) {
        this((String)null, manager.get(EntityData.class), manager.getStepTime());
    }
    public FactoryInfo(GameSystemsState state) {
        this((String)null, state.get(EntityData.class), state.getStepTime());
    }
    public FactoryInfo(EntityData ed, AppStateManager manager) {
        this((String)null, ed, manager.getState(GameSystemsState.class).getStepTime());
    }
    public FactoryInfo(EntityData ed, Application app) {
        this((String)null, ed, app.getStateManager());
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPrefab(Prefab prefab) {
        name = prefab.getName(ed);
    }
    public void setEntityData(EntityData ed) {
        this.ed = ed;
    }
    public void setTime(SimTime time) {
        this.time = time;
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
