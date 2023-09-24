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
public class Drive implements EntityComponent {
    
    private final boolean forward;

    public Drive(boolean forward) {
        this.forward = forward;
    }

    public boolean isForwardDrive() {
        return forward;
    }
    public int asNumber() {
        return forward ? 1 : -1;
    }
    public Drive reverse() {
        return new Drive(!forward);
    }
    @Override
    public String toString() {
        return "Drive{" + "forward=" + forward + '}';
    }
    
}
