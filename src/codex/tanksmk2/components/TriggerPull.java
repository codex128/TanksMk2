/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 * Indicates if the user is pulling the trigger.
 * 
 * <p>Is not directly linked to shooting.
 * 
 * @author codex
 */
public class TriggerPull implements EntityComponent {
    
    private final boolean pulled;
    
    public TriggerPull() {
        this(false);
    }
    public TriggerPull(boolean pulled) {
        this.pulled = pulled;
    }

    public boolean isPulled() {
        return pulled;
    }
    @Override
    public String toString() {
        return "TriggerInput{" + "pulled=" + pulled + '}';
    }
    
}
