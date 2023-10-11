/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import codex.tanksmk2.util.Curve;
import codex.tanksmk2.util.Handle;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class EntityCurve extends Curve implements EntityComponent {
    
    private final double start;

    public EntityCurve(double start, Handle... handles) {
        this(start, false, handles);
    }
    public EntityCurve(double start, boolean sort, Handle... handles) {
        super(sort, handles);
        this.start = start;
    }
    
    public double getStartTime() {
        return start;
    }
    
}
