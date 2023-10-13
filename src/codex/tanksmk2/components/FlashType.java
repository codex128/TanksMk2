/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 * Type of muzzle flash.
 * 
 * @author codex
 */
public class FlashType implements EntityComponent {
    
    public static final int FLAME = 0;
    public static final FlashType DEFAULT = new FlashType(FLAME);
    
    private final int type;
    
    public FlashType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
    @Override
    public String toString() {
        return "FlashType{" + "type=" + type + '}';
    }
    
}
