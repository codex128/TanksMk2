/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.collision;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsRayTestResult;
import com.jme3.collision.CollisionResult;
import com.jme3.math.Ray;
import java.util.Iterator;

/**
 *
 * @author codex
 */
public class SegmentedRaytest implements Iterable<PhysicsRayTestResult> {
    
    private PhysicsSpace space;
    private Ray ray;
    
    public SegmentedRaytest(PhysicsSpace space, Ray ray) {
        this.space = space;
        this.ray = ray;
    }
    
    @Override
    public RaySegmentIterator iterator() {
        return new RaySegmentIterator(this);
    }
    
    public PhysicsSpace getSpace() {
        return space;
    }
    public Ray getRay() {
        return ray;
    }
    
}
