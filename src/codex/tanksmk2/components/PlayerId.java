/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class PlayerId implements EntityComponent {
    
    private static int nextId = 0;    
    private final int id;

    public PlayerId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    @Override
    public String toString() {
        return "Player{" + "number=" + id + '}';
    }
    @Override
    public int hashCode() {
        return id;
    }
    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj != null && getClass() == obj.getClass() && this.id == ((PlayerId)obj).id);
    }
    
    public static PlayerId create() {
        return new PlayerId(nextId++);
    }
    
}
