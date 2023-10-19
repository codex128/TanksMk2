/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import codex.tanksmk2.components.*;
import codex.tanksmk2.util.GameUtils;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

/**
 * Creates entities based on customer entities.
 * 
 * @author codex
 */
public class CustomerEntityFactory {
    
    private CustomerEntityFactory() {}
    
    public static EntityId create(FactoryInfo info, EntityId customer, boolean adopt) {
        EntityId id = switch (info.name) {
            case "muzzleflash" -> createMuzzleflash(info, customer, adopt);
            case "explosion" -> createExplosion(info, customer, adopt);
            default -> throw new UnsupportedOperationException("Unsupported entity type!");
        };
        if (id != null && adopt) {
            info.ed.setComponent(id, new Parent(customer));
        }
        return id;
    }
    
    public static EntityId[] create(FactoryInfo info, PrefabComponent prefabs, EntityId customer, boolean adopt) {
        if (prefabs == null) {
            return null;
        }
        var array = new EntityId[prefabs.getPrefabs().length];
        int i = 0;
        for (var p : prefabs.getPrefabs()) {
            info.setPrefab(p);
            array[i++] = create(info, customer, adopt);
        }
        return array;
    }
    
    public static EntityId[] create(FactoryInfo info, Class<? extends PrefabComponent> compType, EntityId parent, boolean adopt) {
        return create(info, info.getEntityData().getComponent(parent, compType), parent, adopt);
    }
    
    /*=============
     |  Builders  |
     =============*/
    
    public static EntityId createMuzzleflash(FactoryInfo info, EntityId customer, boolean adopt) {
        var t = getTransform(info.ed, customer, adopt);
        return EntityFactory.createMuzzleflash(info, t.getTranslation(), new Rotation(t), .04);
    }
    
    public static EntityId createExplosion(FactoryInfo info, EntityId customer, boolean adopt) {
        var t = getTransform(info.ed, customer, adopt);
        return EntityFactory.createExplosion(info, t.getTranslation(), 25, 25);
    }
    
    /*==============
     |  Utilities  |
     ==============*/
    
    private static Transform getTransform(EntityData ed, EntityId id, boolean local) {
        if (local) {
            return Transform.IDENTITY;
        } else {
            return GameUtils.getWorldTransform(ed, id);
        }
    }
    
}
