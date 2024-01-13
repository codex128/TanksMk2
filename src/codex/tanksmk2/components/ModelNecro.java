/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import codex.tanksmk2.factories.Prefab;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

/**
 * Transfers control of a model from the target entity to this entity.
 * 
 * @author codex
 */
public class ModelNecro implements EntityComponent {
    
    private Prefab prefab;

    public ModelNecro(Prefab prefab) {
        this.prefab = prefab;
    }

    public Prefab getPrefab() {
        return prefab;
    }

    @Override
    public String toString() {
        return "ModelNecro{" + "prefab=" + prefab + '}';
    }
    
}
