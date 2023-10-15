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
    
    private static int nextId = -1;
    
    private final int id;
    private final int variation;
    
    public Prefab(int id) {
        this(id, 0);
    }
    public Prefab(int id, int variation) {
        this.id = id;
        this.variation = variation;
    }

    public int getId() {
        return id;
    }
    public int getVariation() {
        return variation;
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
    public static Prefab create(String name, int variation, EntityData ed) {
        return new Prefab(ed.getStrings().getStringId(name, true), variation);
    }
    public static Prefab generateUnique() {
        return new Prefab(nextId--);
    }
    
}
