/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class Force implements EntityComponent {
    
    private final Vector3f linear = new Vector3f();
    private final Vector3f angular = new Vector3f();
    private final boolean momentary;
    
    public Force(Vector3f linear) {
        this(linear, true);
    }
    public Force(Vector3f linear, boolean momentary) {
        this.linear.set(linear);
        this.momentary = momentary;
    }
    public Force(Vector3f linear, Vector3f angular) {
        this(linear, angular, true);
    }
    public Force(Vector3f linear, Vector3f angular, boolean momentary) {
        this(linear, momentary);
        this.angular.set(angular);
    }

    public Vector3f getLinear() {
        return linear;
    }
    public Vector3f getAngular() {
        return angular;
    }
    public boolean isMomentary() {
        return momentary;
    }
    
}
