/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import codex.tanksmk2.factories.LootInfo;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public abstract class LootComponent implements EntityComponent {
    
    private final LootInfo[] loot;

    public LootComponent(LootInfo... loot) {
        this.loot = loot;
    }

    public LootInfo[] getLoot() {
        return loot;
    }
    
}
