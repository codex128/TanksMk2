/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.util;

import com.jme3.math.FastMath;
import com.simsilica.es.EntityComponent;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author codex
 */
public class Curve {
    
    private static final Comparator<Handle> SORT = (h1, h2) -> {
        if (h1.getTime() < h2.getTime()) return -1;
        else if (h1.getTime() > h2.getTime()) return 1;
        else return 0;
    };
    
    private final Handle[] handles;
    private float scalar = 1f;
    
    public Curve(Handle... handles) {
        this(false, handles);
    }
    public Curve(boolean sort, Handle... handles) {
        this.handles = handles;
        if (this.handles.length == 0) {
            throw new IllegalArgumentException("Cannot calculate curve having zero handles!");
        }
        if (sort) Arrays.sort(this.handles, SORT);
    }
    
    public float interpolate(float t) {
        int i = getInterval(t);
        if (i < 0) {
            return handles[0].getValue();
        }
        else if (i >= handles.length-1) {
            return handles[handles.length-1].getValue();
        }
        return interpolateIntervalValues(i, handles[i].getEase().apply(getIntervalProgress(i, t)))*scalar;
    }
    public Handle[] getHandles() {
        return handles;
    }
    
    private int getInterval(float t) {
        int below = -1;
        for (var h : handles) {
            if (h.getPosition().x > t) {
                break;
            }
            below++;
        }
        return below;
    }
    private float getIntervalProgress(int i, float t) {
        // algebra:
        // t = (t2 - t1) * p + t1     (basic linear interpolation)
        // t - t1 = (t2 - t1) * p
        // (t - t1) / (t2 - t1) = p   (reversed linear interpolation)
        return (t-handles[i].getTime())/(handles[i+1].getTime()-handles[i].getTime());
    }
    private float interpolateIntervalValues(int i, float p) {
        return FastMath.interpolateLinear(p, handles[i].getValue(), handles[i+1].getValue());
    }
    
}
