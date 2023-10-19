/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.curves;

import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;

/**
 *
 * @author codex
 */
public class Wave implements Interpolator {
    
    public enum Mode {
        Sine, Saw, Binary;
    }
    
    private final Mode mode;
    private final Vector2f position;
    private final float amplitude, frequency;
    private final float midpoint;
    
    public Wave(Mode mode, Vector2f position, float amplitude, float frequency) {
        this(mode, position, amplitude, frequency, 0.5f);
    }
    public Wave(Mode mode, Vector2f position, float amplitude, float frequency, float midpoint) {
        this.mode = mode;
        this.position = new Vector2f(position);
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.midpoint = midpoint;
    }
    
    @Override
    public float interpolate(float t) {
        return switch (mode) {
            case Sine   -> interpolateSine(t);
            case Saw    -> interpolateSaw(t);
            case Binary -> interpolateBinary(t);
        };
    }
    
    private float interpolateSine(float t) {
        return FastMath.sin(t*frequency+position.x)*amplitude+position.y;
    }
    private float interpolateSaw(float t) {
        return ((t+position.x)%frequency/frequency)*amplitude+position.y;
    }
    private float interpolateBinary(float t) {
        float p = (t+position.x)%frequency/frequency;
        if (p > midpoint) {
            return amplitude+position.y;
        } else {
            return position.y;
        }
    }
    
    public Mode getMode() {
        return mode;
    }
    public Vector2f getPosition() {
        return position;
    }
    public float getAmplitude() {
        return amplitude;
    }
    public float getFrequency() {
        return frequency;
    }
    public float getMidpoint() {
        return midpoint;
    }
    
}
