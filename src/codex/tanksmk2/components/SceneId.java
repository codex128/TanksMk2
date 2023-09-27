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
public class SceneId implements EntityComponent {
    
    private static long nextId = 0;    
    private final long id;
    
    public SceneId() {
        this(-1);
    }
    public SceneId(long id) {
        this.id = id;
    }
    public SceneId(SceneId id) {
        this(id.id);
    }

    public long getId() {
        return id;
    }
    @Override
    public String toString() {
        return "SceneId{" + "id=" + id + '}';
    }
    
    public static SceneId create() {
        return new SceneId(nextId++);
    }
    
}
