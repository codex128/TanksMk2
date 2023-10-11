/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.jme3.light.SpotLight;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class InfluenceCone implements EntityComponent {
    
    private final float innerAngle;
    private final float outerAngle;

    public InfluenceCone(float innerAngle, float outerAngle) {
        this.innerAngle = innerAngle;
        this.outerAngle = outerAngle;
    }

    public float getInnerAngle() {
        return innerAngle;
    }
    public float getOuterAngle() {
        return outerAngle;
    }
    public void applyToSpotLight(SpotLight light) {
        light.setSpotInnerAngle(innerAngle);
        light.setSpotOuterAngle(outerAngle);
    }
    @Override
    public String toString() {
        return "InfluenceCone{" + "innerAngle=" + innerAngle + ", outerAngle=" + outerAngle + '}';
    }
    
}
