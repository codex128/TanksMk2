/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import codex.tanksmk2.components.*;
import codex.tanksmk2.util.GameUtils;
import com.simsilica.es.EntityId;

/**
 * Creates entities based on parents.
 * 
 * @author codex
 */
public class ParentEntityFactory {
    
    private ParentEntityFactory() {}
    
    public static EntityId create(FactoryInfo info, EntityId parent, boolean adopt) {
        EntityId id = switch (info.name) {
            case "muzzleflash" -> createMuzzleflash(info, parent);
            default -> null;
        };
        if (id != null && adopt) {
            info.ed.setComponent(id, new Parent(parent));
        }
        return id;
    }
    
    public static EntityId[] create(FactoryInfo info, PrefabComponent prefabs, EntityId parent, boolean adopt) {
        if (prefabs == null) {
            return null;
        }
        var array = new EntityId[prefabs.getPrefabs().length];
        int i = 0;
        for (var p : prefabs.getPrefabs()) {
            info.setPrefab(p);
            array[i++] = create(info, parent, adopt);
        }
        return array;
    }
    
    public static EntityId[] create(FactoryInfo info, Class<? extends PrefabComponent> compType, EntityId parent, boolean adopt) {
        return create(info, info.getEntityData().getComponent(parent, compType), parent, adopt);
    }
    
    public static EntityId createMuzzleflash(FactoryInfo info, EntityId parent) {
        var id = info.ed.createEntity();
        info.ed.setComponents(id,
            new GameObject("muzzleflash"),
            ModelInfo.create("muzzleflash", info.ed),
            new Position(),
            new Rotation(),
            GameUtils.duration(info.time, .2)
        );
        return id;
    }
    
}
