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
public class ReflectOnTouch implements EntityComponent {
    
    private final boolean consume;

    public ReflectOnTouch() {
        this(true);
    }
    public ReflectOnTouch(boolean consume) {
        this.consume = consume;
    }

    public boolean isConsumeBounce() {
        return consume;
    }
    @Override
    public String toString() {
        return "ReflectOnContact{" + "consume=" + consume + '}';
    }
    
}
