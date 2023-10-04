/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import com.simsilica.es.EntityData;

/**
 *
 * @author codex
 */
public class Prefab {
    
    private final int id;
    
    public Prefab(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public String getName(EntityData ed) {
        return ed.getStrings().getString(id);
    }
    @Override
    public String toString() {
        return "Prefab{" + "id=" + id + '}';
    }
    
    public static Prefab create(String name, EntityData ed) {
        return new Prefab(ed.getStrings().getStringId(name, true));
    }
    
}
