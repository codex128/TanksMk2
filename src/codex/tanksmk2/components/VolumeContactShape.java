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
public class VolumeContactShape implements EntityComponent {
    
    public static final int SPHERE = 0, BOX = 1;
    
    private final int type;
    private final float size;

    public VolumeContactShape(int type, float size) {
        this.type = type;
        this.size = size;
    }

    public int getType() {
        return type;
    }
    public float getSize() {
        return size;
    }
    @Override
    public String toString() {
        return "VolumeContact{" + "type=" + type + ", size=" + size + '}';
    }
    
}
