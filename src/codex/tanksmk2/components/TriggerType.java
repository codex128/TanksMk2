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
public class TriggerType implements EntityComponent {
    
    public static final int SEMI = 0, AUTO = 1;
    
    private final int type;

    public TriggerType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
    
}
