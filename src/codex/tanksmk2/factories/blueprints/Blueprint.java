/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package codex.tanksmk2.factories.blueprints;

import codex.tanksmk2.components.Position;
import codex.tanksmk2.components.Rotation;
import codex.tanksmk2.factories.FactoryInfo;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public interface Blueprint {
    
    /**
     * Creates a set of entities from scratch.
     * 
     */
    public void create();
    
    /**
     * Creates a set of entities based on components of a customer entity.
     * 
     * @param customer 
     */
    public void create(EntityId customer);
    
    public void setEntityData(EntityData ed);
    
    public void setTime(SimTime time);
    
    public EntityData getEntityData();
    
    public SimTime getTime();
    
    public EntityId getMainEntity();
    
    public default void setCreationInfo(FactoryInfo info) {
        setEntityData(info.ed);
        setTime(info.time);
    }
    
    public default void setCreationInfo(EntityData ed, SimTime time) {
        setEntityData(ed);
        setTime(time);
    }
    
    public default void setPosition(Position position) {
        getEntityData().setComponent(getMainEntity(), position);
    }
    
    public default void setPosition(Vector3f position) {
        setPosition(new Position(position));
    }
    
    public default void setPosition(float x, float y, float z) {
        setPosition(new Position(x, y, z));
    }
    
    public default void setRotation(Quaternion rotation) {
        setRotation(new Rotation(rotation));
    }
    
    public default void setRotation(Rotation rotation) {
        getEntityData().setComponent(getMainEntity(), rotation);
    }
    
}
