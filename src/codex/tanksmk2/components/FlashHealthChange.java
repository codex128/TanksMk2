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
public class FlashHealthChange implements EntityComponent {
    
    private final String matParam;
    private final float intensity;

    public FlashHealthChange(String matParam) {
        this(matParam, 1);
    }
    public FlashHealthChange(String matParam, float intensity) {
        this.matParam = matParam;
        this.intensity = intensity;
    }

    public String getMatParam() {
        return matParam;
    }
    public float getIntensity() {
        return intensity;
    }

    @Override
    public String toString() {
        return "FlashHealthChange{" + "intensity=" + intensity + '}';
    }
    
}
