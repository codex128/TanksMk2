/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import codex.tanksmk2.factories.Prefab;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class PrefabComponent implements EntityComponent {
    
    private final Prefab[] prefabs;

    public PrefabComponent(Prefab[] prefabs) {
        this.prefabs = prefabs;
    }

    public Prefab[] getPrefabs() {
        return prefabs;
    }
    
}
