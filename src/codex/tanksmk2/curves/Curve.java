/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.curves;

import com.jme3.math.FastMath;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author codex
 */
public class Curve implements Interpolator {
    
    private static final Comparator<Handle> SORT = (h1, h2) -> {
        return Float.compare(h1.getTime(), h2.getTime());
    };
    
    private final Handle[] handles;
    private float scale = 1f;
    private boolean repeat = false;
    
    public Curve(Curve curve) {
        this(curve.handles);
        scale = curve.scale;
        repeat = curve.repeat;
    }
    public Curve(Handle... handles) {
        this.handles = handles;
        if (this.handles.length == 0) {
            throw new IllegalArgumentException("Cannot calculate curve having zero handles!");
        }
    }
    
    @Override
    public float interpolate(float t) {
        int i = getInterval(!repeat ? t : wrapTime(t));
        if (repeat && (i < 0 || i >= handles.length-1)) {
            // technically, this could never happen, but it'd be a pain to debug if it did
            throw new IllegalStateException("Interval index outside domain on wrapped mode!");
        }
        if (i < 0) {
            return handles[0].getValue();
        } else if (i >= handles.length-1) {
            return handles[handles.length-1].getValue();
        }
        return interpolateIntervalValues(i, handles[i].getEase().apply(getIntervalProgress(i, t)))*scale;
    }
    
    private int getInterval(float t) {
        int i = -1;
        for (var h : handles) {
            if (h.getPosition().x > t) {
                break;
            }
            i++;
        }
        return i;
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
    private float wrapTime(float t) {
        return t%getLength();
    }
    private float getLength() {
        return handles[handles.length-1].getTime()-handles[0].getTime();
    }
    
    public void setScale(float scale) {
        this.scale = scale;
    }
    public void setRepeating(boolean repeat) {
        this.repeat = repeat;
    }
    
    public Handle[] getHandles() {
        return handles;
    }
    public float getScale() {
        return scale;
    }
    public boolean isRepeating() {
        return repeat;
    }
    
}
