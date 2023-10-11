/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.util;

import com.jme3.math.EaseFunction;
import com.jme3.math.Vector2f;

/**
 *
 * @author codex
 */
public class Handle {
    
    private Vector2f position;
    private EaseFunction ease;
    
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
