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
public class CameraPriority implements EntityComponent {
    
    public static final int LOW = 0, MID = 1, HIGH = 2;
    public static final CameraPriority NO_PRIORITY = new CameraPriority(-1);
    
    private final int level;
    
    public CameraPriority() {
        this(LOW);
    }
    public CameraPriority(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
    @Override
    public String toString() {
        return "CinematicPriority{" + "level=" + level + '}';
    }
    
}
