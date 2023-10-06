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
public class CreateEffectOnRicochet implements EntityComponent {
    
    private final Prefab prefab;

    public CreateEffectOnRicochet(Prefab prefab) {
        this.prefab = prefab;
    }

    public Prefab getPrefab() {
        return prefab;
    }
    @Override
    public String toString() {
        return "CreateEffectOnRicochet{" + "prefab=" + prefab + '}';
    }
    
}
