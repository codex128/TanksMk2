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
public class AxisConstraint implements EntityComponent {
    
    public static final AxisConstraint ALL = new AxisConstraint();
    
    public boolean x, y, z;
    
    public AxisConstraint() {
        x = y = z = true;
    }
    public AxisConstraint(boolean x, boolean y, boolean z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public boolean isX() {
        return x;
    }
    public boolean isY() {
        return y;
    }
    public boolean isZ() {
        return z;
    }
    
    public Vector3f apply(Vector3f vector, float defValue) {
        return new Vector3f(
            x ? vector.x : defValue,
            y ? vector.y : defValue,
            z ? vector.z : defValue
        );
    }
    public Vector3f applyLocal(Vector3f vector, float defValue) {
        if (!x) vector.x = defValue;
        if (!y) vector.y = defValue;
        if (!z) vector.z = defValue;
        return vector;
    }
    
}
