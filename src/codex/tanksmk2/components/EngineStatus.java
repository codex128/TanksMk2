/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 * A sort of output component that indicates the engine status of the entity.
 * 
 * @author codex
 */
public class EngineStatus implements EntityComponent {
    
    public static final int IDLE = 0, WORKING = 1, BOOSTING = 2;
    
    private final int status;

    public EngineStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
    public boolean isStatus(int s) {
        return status == s;
    }
    
}
