/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class Stats implements EntityComponent {    
    
    public static final int ARMOR = 0, MOVE_SPEED = 1, BULLET_ACCEL = 2, BOUNCES = 3, FIRERATE = 4;
    
    private final float[] values = new float[5];

    public Stats() {
        setValues(0f);
    }
    public Stats(float... values) {
        setValues(values);
    }
    public Stats(Stats stats) {
        setValues(stats.values);
    }
    
    private void setValues(float v) {
        for (int i = 0; i < values.length; i++) {
            values[i] = v;
        }
    }
    private void setValues(float... v) {
        for (int i = 0; i < values.length; i++) {
            values[i] = v[i];
        }
    }
    
    // initialization only
    public Stats set(int i, float v) {
        values[i] = v;
        return this;
    }
    
    public float get(int i) {
        return values[i];
    }
    public float[] getValues() {
        return values;
    }
    
}
