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
public class CameraId implements EntityComponent {
    
    private static int nextId = 0;    
    private final int id;

    public CameraId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    @Override
    public String toString() {
        return "CameraId{" + "id=" + id + '}';
    }
    
    public static CameraId create() {
        return new CameraId(nextId++);
    }
    
}
