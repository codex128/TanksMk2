/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;

/**
 *
 * @author codex
 */
public class ModelInfo implements EntityComponent {
    
    private int id;

    public ModelInfo(int id) {
        this.id = id;
    }
    
    public int getModelId() {
        return id;
    }
    public String getModelName(EntityData ed) {
        return ed.getStrings().getString(id);
    }
    @Override
    public String toString() {
        return "ModelInfo{" + "id=" + id + '}';
    }
    
    public static ModelInfo create(String name, EntityData ed) {
        return new ModelInfo(ed.getStrings().getStringId(name, true));
    }
    
}
