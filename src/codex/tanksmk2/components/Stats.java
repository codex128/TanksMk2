/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package codex.tanksmk2.components;

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
    public static final StatDefinition[] DEFINITIONS = {
        def("armor", 0),
        def("move-speed", 1),
        def("bullet-speed", 10),
        def("bounces", 0),
        def("firerate", .1f),
        def("move-accel", 1f),
        def("damage", 0),
    };
    
    private final float[] values = new float[DEFINITIONS.length];
    
    public Stats() {
        this(false);
    }
    public Stats(boolean useDefaults) {
        if (useDefaults) {
            setValues(DEFINITIONS);
        } else {
            setValues(0);
        }
    }
    public Stats(float... values) {
        setValues(values);
    }
    public Stats(Stats stats) {
        setValues(stats.values);
    }
    
    private void setValues(StatDefinition... defaults) {
        for (int i = 0; i < values.length && i < defaults.length; i++) {
            values[i] = defaults[i].value;
        }
    }
    private void setValues(float... v) {
        for (int i = 0; i < values.length && i < v.length; i++) {
            values[i] = v[i];
        }
    }
    private void setValues(float v) {
        for (int i = 0; i < values.length; i++) {
            values[i] = v;
        }
    }
    
    public Stats set(int i, float v) {
        values[i] = v;
        return this;
    }
    public Stats addLocal(Stats add) {
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i]+add.values[i];
        }
        return this;
    }
    public Stats add(Stats add, Stats store) {
        if (store == null) {
            store = new Stats();
        }
        for (int i = 0; i < values.length; i++) {
            store.values[i] = values[i]+add.values[i];
        }
        return store;
    }
    
    public float get(int i) {
        return values[i];
    }
    public float[] getValues() {
        return values;
    }
    
    public static float getDefault(int i) {
        return DEFINITIONS[i].value;
    }
    public static StatDefinition getDefinition(int i) {
        return DEFINITIONS[i];
    }
    private static StatDefinition def(String key, float value) {
        return new StatDefinition(key, value);
    }
    
    public static final class StatDefinition {
        public final String key;
        public final float value;
        private StatDefinition(String key, float value) {
            this.key = key;
            this.value = value;
        }
    }
    
}
