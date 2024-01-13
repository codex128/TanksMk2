/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import codex.tanksmk2.bullet.GeometricShape;
import codex.tanksmk2.components.*;
import codex.tanksmk2.states.ModelViewState;
import codex.tanksmk2.util.GameUtils;
import codex.tanksmk2.util.debug.ObserveTransform;
import com.jme3.math.ColorRGBA;
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
        String observe = getUserData(spatial, "ObserveTransform", (String)null);
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
        if (observe != null) {
            info.ed.setComponent(wall, ObserveTransform.INSTANCE);
        }
        return wall;
    }
    
    public static EntityId createDecor(FactoryInfo info, Spatial spatial) {
        double mass = getUserData(spatial, "Mass", 0.0);
        String shape = getUserData(spatial, "Shape", GeometricShape.DynamicMesh.getName());
        var id = info.ed.createEntity();
        info.ed.setComponents(id,
            new GameObject("decor"),
            ModelInfo.create(ModelViewState.CACHE, info.ed),
            new GeometricShapeInfo(Prefab.generateUnique(), GeometricShape.value(shape)),
            new SpawnPosition(spatial.getWorldTranslation(), spatial.getWorldRotation()),
            new Mass((float)mass),
            new Position(spatial.getLocalTranslation()),
            new Rotation(spatial.getLocalRotation()),
            KillBulletOnTouch.INSTANCE
        );
        return id;
    }
    
    /*=============
     |  Lighting  |
     =============*/
    
    public static EntityId createLight(FactoryInfo info, Spatial spatial) {
        var type = EntityLight.valueOf(getUserData(spatial, "Type", new NullPointerException("Missing light type userdata!")));
        var color = GameUtils.colorFromArrayList(spatial.getUserData("Color"), 0);
        if (color == null) {
            color = ColorRGBA.White;
        }
        var light = switch (type) {
            case EntityLight.DIRECTIONAL -> createDirectionalLight(info, spatial);
            case EntityLight.POINT       -> createPointLight(info, spatial);
            case EntityLight.SPOT        -> createSpotLight(info, spatial);
            case EntityLight.AMBIENT     -> createAmbientLight(info, spatial);
            case EntityLight.PROBE       -> createLightProbe(info, spatial);
            default                      -> createAmbientLight(info, spatial);
        };
        // components shared by all light types
        info.ed.setComponents(light,
            new GameObject("light"),
            new EntityLight(type, color)
        );
        return light;
    }
    
    public static EntityId createDirectionalLight(FactoryInfo info, Spatial spatial) {
        var id = info.ed.createEntity();
        info.ed.setComponent(id, new Rotation(spatial.getLocalRotation()));
        return id;
    }
    
    public static EntityId createPointLight(FactoryInfo info, Spatial spatial) {
        double power = getUserData(spatial, "Power", new NullPointerException("Missing light power userdata!"));
        var id = info.ed.createEntity();
        info.ed.setComponents(id,
            new Position(spatial.getLocalTranslation()),
            new Power(power)
        );
        return id;
    }
    
    public static EntityId createSpotLight(FactoryInfo info, Spatial spatial) {
        double power = getUserData(spatial, "Power", new NullPointerException("Missing light power userdata!"));
        double[] angles = getUserData(spatial, "Angles", new NullPointerException("Missing light angles userdata!"));
        if (angles.length != 2) {
            throw new IllegalArgumentException("Angles array must have 2 elements!");
        }
        var id = info.ed.createEntity();
        info.ed.setComponents(id,
            new Position(spatial.getLocalTranslation()),
            new Rotation(spatial.getLocalRotation()),
            new Power(power),
            new InfluenceCone(angles[0], angles[1])
        );
        return id;
    }
    
    public static EntityId createAmbientLight(FactoryInfo info, Spatial spatial) {
        return info.ed.createEntity();
    }
    
    public static EntityId createLightProbe(FactoryInfo info, Spatial spatial) {
        String path = getUserData(spatial, "Source", new NullPointerException("Missing light probe source path!"));
        var id = info.ed.createEntity();
        info.ed.setComponents(id,
            new Position(spatial.getLocalTranslation()),
            new LightProbeSource(path)
        );
        return id;
    }
    
    /*==============
     |  Utilities  |
     ==============*/
    
    private static <T> T getUserData(Spatial spatial, String name, NullPointerException exception) {
        T userdata = spatial.getUserData(name);
        if (userdata == null) {
            throw exception;
        }
        return userdata;
    }
    
    private static <T> T getUserData(Spatial spatial, String name, T defaultValue) {
        T userdata = spatial.getUserData(name);
        if (userdata != null) {
            return userdata;
        } else {
            return defaultValue;
        }
    }
    
}
