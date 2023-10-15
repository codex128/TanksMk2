/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package codex.tanksmk2.components;

import codex.j3map.J3map;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class Stats implements EntityComponent {    
    
    public static final int
            ARMOR = 0, MOVE_SPEED = 1, BULLET_SPEED = 2,
            BOUNCES = 3,  FIRERATE = 4, MOVE_ACCEL = 5,
            DAMAGE = 6;
    private static final StatDefault[] DEFAULTS = {
        def("armor", 0),
        def("move-speed", 1),
        def("bullet-speed", 10),
        def("bounces", 0),
        def("firerate", .1f),
        def("move-accel", 0.5f),
        def("damage", 0),
    };
    
    private final float[] values = new float[DEFAULTS.length];

    public Stats() {
        setValues(DEFAULTS);
    }
    public Stats(float... values) {
        setValues(values);
    }
    public Stats(Stats stats) {
        setValues(stats.values);
    }
    
    private void setValues(StatDefault... defaults) {
        for (int i = 0; i < values.length && i < defaults.length; i++) {
            values[i] = defaults[i].value;
        }
    }
    private void setValues(float... v) {
        for (int i = 0; i < values.length && i < v.length; i++) {
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
    
    public static float getDefault(int i) {
        return DEFAULTS[i].value;
    }
    private static StatDefault def(String key, float value) {
        return new StatDefault(key, value);
    }
    private static final class StatDefault {
        String key;
        float value;
        StatDefault(String key, float value) {
            this.key = key;
            this.value = value;
        }
    }
    
}
