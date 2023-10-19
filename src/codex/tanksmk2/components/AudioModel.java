/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import codex.tanksmk2.factories.Prefab;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;

/**
 *
 * @author codex
 */
public class AudioModel implements EntityComponent {
    
    private final Prefab prefab;

    public AudioModel(Prefab prefab) {
        this.prefab = prefab;
    }
    
    public Prefab getPrefab() {
        return prefab;
    }    
    public String getName(EntityData ed) {
        return prefab.getName(ed);
    }
    
    public static AudioModel create(String name, EntityData ed) {
        return new AudioModel(Prefab.create(name, ed));
    }
    
}
