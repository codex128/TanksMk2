/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import codex.tanksmk2.components.LootComponent;
import codex.tanksmk2.components.MatValue;
import codex.tanksmk2.components.TargetTo;
import codex.tanksmk2.util.GameUtils;
import com.jme3.shader.VarType;
import com.simsilica.es.EntityId;

/**
 * Creates loot entities.
 * 
 * @author codex
 */
public class LootEntityFactory {
    
    /**
     * Creates an array of loot entities.
     * 
     * @param info
     * @param id
     * @param loot
     * @return 
     */
    public static EntityId[] create(FactoryInfo info, EntityId id, LootComponent loot) {
        var array = new EntityId[loot.getLoot().length];
        for (int i = 0; i < array.length; i++) {
            var in = loot.getLoot()[i];
            array[i] = switch (in.getPrefab().getName(info.ed)) {
                case "MatParam" -> createMatParam(info, id, in);
                default -> null;
            };
        }
        return array;
    }
    
    public static EntityId createMatParam(FactoryInfo info, EntityId id, LootInfo lootInfo) {
        var setter = info.ed.createEntity();
        info.ed.setComponents(setter,
            new MatValue(
                (String)lootInfo.getArguments()[0],
                (VarType)lootInfo.getArguments()[1],
                lootInfo.getArguments()[2]
            ),
            new TargetTo(id),
            GameUtils.duration(info.time, 0.1)
        );
        return setter;
    }
    
}
