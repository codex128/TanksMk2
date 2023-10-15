/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class Bounces implements EntityComponent {
    
    private final int bouncesRemaining, bouncesMade;

    public Bounces(int bouncesRemaining) {
        this(bouncesRemaining, 0);
    }
    private Bounces(int bouncesRemaining, int bouncesMade) {
        this.bouncesRemaining = Math.max(bouncesRemaining, -1);
        this.bouncesMade = bouncesMade;
    }

    public int getBouncesRemaining() {
        return bouncesRemaining;
    }
    public int getBouncesMade() {
        return bouncesMade;
    }
    public boolean isExhausted() {
        return bouncesRemaining < 0;
    }
    @Override
    public String toString() {
        return "Bounces{" + "remaining=" + bouncesRemaining + ", made=" + bouncesMade + '}';
    }
    
    public Bounces increment() {
        return new Bounces(bouncesRemaining-1, bouncesMade+1);
    }
    
}
