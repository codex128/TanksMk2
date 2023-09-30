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
public class Firerate implements EntityComponent {
    
    private final double value;
    
    public Firerate() {
        this(0);
    }
    private Firerate(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
    public boolean isComplete() {
        return value <= 0;
    }
    public Firerate increment(double tpf) {
        return new Firerate(value-tpf);
    }
    @Override
    public String toString() {
        return "Firerate{" + "value=" + value + '}';
    }
    
}
