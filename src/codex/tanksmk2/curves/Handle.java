/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.curves;

import com.jme3.math.EaseFunction;
import com.jme3.math.Vector2f;

/**
 *
 * @author codex
 */
public class Handle {
    
    private final Vector2f position;
    private final EaseFunction ease;
    
    public Handle(float x, float y) {
        this(new Vector2f(x, y), null);
    }
    public Handle(Vector2f position) {
        this(position, null);
    }
    public Handle(float x, float y, EaseFunction ease) {
        this(new Vector2f(x, y), ease);
    }
    public Handle(Vector2f position, EaseFunction ease) {
        this.position = position;
        this.ease = ease;
    }

    public Vector2f getPosition() {
        return position;
    }
    public float getTime() {
        return position.x;
    }
    public float getValue() {
        return position.y;
    }
    public EaseFunction getEase() {
        return ease;
    }
    
}
