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
public class Inventory implements EntityComponent {
    
    public static final int BULLETS = 0, MISSILES = 1, GRENADES = 2, PARTS = 3;    
    protected final int[] values = new int[4];
    
    public Inventory() {
        setValues(0);
    }
    public Inventory(int v) {
        setValues(v);
    }
    public Inventory(int... v) {
        setValues(v);
    }
    public Inventory(Inventory inv) {
        setValues(inv.values);
    }
    public Inventory(int i, int v, Inventory inv) {
        setValues(inv.values);
        values[i] = v;
    }
    
    private void setValues(int v) {
        for (int i = 0; i < values.length; i++) {
            values[i] = v;
        }
    }
    private void setValues(int... v) {
        for (int i = 0; i < values.length; i++) {
            values[i] = v[i];
        }
    }
    
    public int get(int i) {
        return values[i];
    }
    public int[] getValues() {
        return values;
    }
    
}
