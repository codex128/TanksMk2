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
public class AudioInfo implements EntityComponent {
    
    private final Prefab prefab;

    public AudioInfo(Prefab prefab) {
        this.prefab = prefab;
    }
    
    public Prefab getPrefab() {
        return prefab;
    }    
    public String getName(EntityData ed) {
        return prefab.getName(ed);
    }
    
    public static AudioInfo create(String name, EntityData ed) {
        return new AudioInfo(Prefab.create(name, ed));
    }
    
}
