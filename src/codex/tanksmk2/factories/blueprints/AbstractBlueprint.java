/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories.blueprints;

import com.jme3.math.Transform;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public abstract class AbstractBlueprint implements Blueprint {
    
    protected EntityData ed;
    protected SimTime time;
    protected EntityId main;
    protected final Transform tempTransform = new Transform();
    
    @Override
    public void setEntityData(EntityData ed) {
        this.ed = ed;
    }
    @Override
    public void setTime(SimTime time) {
        this.time = time;
    }
    @Override
    public EntityData getEntityData() {
        return ed;
    }
    @Override
    public SimTime getTime() {
        return time;
    }
    @Override
    public EntityId getMainEntity() {
        return main;
    }
    
}
