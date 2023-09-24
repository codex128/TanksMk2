/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.render;

/**
 *
 * @author codex
 */
public class CameraId {
    
    private static long nextId = 0;
    
    private final long id;
    
    public CameraId() {
        this(nextId++);
    }
    public CameraId(long id) {
        this.id = id;
    }
    
    public long getId() {
        return id;
    }
    @Override
    public String toString() {
        return "CameraId{" + id + '}';
    }
    
}
