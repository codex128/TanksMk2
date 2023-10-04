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
    
    private final boolean force;

    public ReflectOnTouch() {
        this(true);
    }
    public ReflectOnTouch(boolean force) {
        this.force = force;
    }

    public boolean isForce() {
        return force;
    }
    @Override
    public String toString() {
        return "ReflectOnContact{" + "force=" + force + '}';
    }
    
}
