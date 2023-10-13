/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import codex.tanksmk2.bullet.GeometricShape;
import codex.tanksmk2.components.*;
import codex.tanksmk2.states.ModelViewState;
import codex.tanksmk2.util.GameUtils;
import com.jme3.scene.Spatial;
import com.simsilica.bullet.Mass;
import com.simsilica.bullet.SpawnPosition;
import com.simsilica.es.EntityId;

/**
 * Creates entities based on spatials.
 * 
 * @author codex
 */
public class SceneEntityFactory {
    
    private SceneEntityFactory() {}
    
    /**
     * Creates an entity based of the spatial and its userdata.
     * 
     * @param info information for entity creation
     * @param spatial spatial to get info from
     * @return id of new entity
     */
    public static EntityId create(FactoryInfo info, Spatial spatial) {
        return switch (info.name) {
            case "node"  -> createNode(info, spatial);
            case "wall"  -> createWall(info, spatial);
            case "light" -> createLight(info, spatial);
            default -> null;
        };
    }
    
    public static EntityId createNode(FactoryInfo info, Spatial spatial) {
        var id = info.ed.createEntity();
        info.ed.setComponents(id,
            new GameObject("node"),
            ModelInfo.create(ModelViewState.CACHE, info.ed),
            new Position(spatial.getLocalTranslation()),
            new Rotation(spatial.getLocalRotation())
        );
        return id;
    }
    
    public static EntityId createWall(FactoryInfo info, Spatial spatial) {
        var wall = info.ed.createEntity();
        info.ed.setComponents(wall,
            new GameObject("wall"),
            // Must set this to CACHE, or else ModelViewState won't cache the spatial for us.
            ModelInfo.create(ModelViewState.CACHE, info.ed),
            new GeometricShapeInfo(Prefab.generateUnique(), GeometricShape.Mesh),
            // physics coordinates are in world coordinates
            new SpawnPosition(spatial.getWorldTranslation(), spatial.getWorldRotation()),
            new Mass(0f),
            new Position(spatial.getLocalTranslation()),
            new Rotation(spatial.getLocalRotation()),
            new ReflectOnTouch()
        );
        return wall;
    }
    
    /*=============
     |  Lighting  |
     =============*/
    
    public static EntityId createLight(FactoryInfo info, Spatial spatial) {
        var type = EntityLight.valueOf(getUserData(spatial, "Type", "Missing light type userdata!"));
        var color = GameUtils.colorArrayList(getUserData(spatial, "Color", "Missing light color userdata!"), 0);
        var light = switch (type) {
            case EntityLight.DIRECTIONAL -> createDirectionalLight(info, spatial);
            case EntityLight.POINT       -> createPointLight(info, spatial);
            case EntityLight.SPOT        -> createSpotLight(info, spatial);
            case EntityLight.AMBIENT     -> createAmbientLight(info, spatial);
            default                      -> createAmbientLight(info, spatial);
        };
        // components shared by all light types
        info.ed.setComponents(light,
            new GameObject("light"),
            new EntityLight(type),
            new LightColor(color)
        );
        return light;
    }
    
    public static EntityId createDirectionalLight(FactoryInfo info, Spatial spatial) {
        var id = info.ed.createEntity();
        info.ed.setComponent(id, new Rotation(spatial.getLocalRotation()));
        return id;
    }
    
    public static EntityId createPointLight(FactoryInfo info, Spatial spatial) {
        double power = getUserData(spatial, "Power", "Missing light power userdata!");
        var id = info.ed.createEntity();
        info.ed.setComponents(id,
            new Position(spatial.getLocalTranslation()),
            new Power(power)
        );
        return id;
    }
    
    public static EntityId createSpotLight(FactoryInfo info, Spatial spatial) {
        double power = getUserData(spatial, "Power", "Missing light power userdata!");
        double[] angles = getUserData(spatial, "Angles", "Missing light angles userdata!");
        if (angles.length != 2) {
            throw new IllegalArgumentException("Angles array must have 2 elements!");
        }
        var id = info.ed.createEntity();
        info.ed.setComponents(id,
            new Position(spatial.getLocalTranslation()),
            new Rotation(spatial.getLocalRotation()),
            new Power(power),
            new LightCone(angles[0], angles[1])
        );
        return id;
    }
    
    public static EntityId createAmbientLight(FactoryInfo info, Spatial spatial) {
        return info.ed.createEntity();
    }
    
    /*==============
     |  Utilities  |
     ==============*/
    
    private static <T> T getUserData(Spatial spatial, String name, String exception) {
        T userdata = spatial.getUserData(name);
        if (userdata == null) {
            throw new NullPointerException(exception);
        }
        return userdata;
    }
    
}
