/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import java.util.Arrays;

/**
 *
 * @author codex
 */
public class Trigger implements EntityComponent {
    
    public static final int INPUT = 0, FIRERATE = 1, AMMO = 2;    
    private final boolean[] flags = new boolean[3];
    
    public Trigger() {
        setFlags(false);
    }
    public Trigger(boolean... flags) {
        setFlags(flags);
    }
    
    private void setFlags(boolean flags) {
        for (int i = 0; i < this.flags.length; i++) {
            this.flags[i] = flags;
        }
    }
    private void setFlags(boolean... flags) {
        for (int i = 0; i < this.flags.length && i < flags.length; i++) {
            this.flags[i] = flags[i];
        }
    }
    private Trigger setFlag(int i, boolean flag) {
        flags[i] = flag;
        return this;
    }
    public Trigger set(int i, boolean flag) {
        return new Trigger(flags).setFlag(i, flag);
    }

    public boolean[] getFlags() {
        return flags;
    }
    public boolean get(int i) {
        return flags[i];
    }
    public boolean allFlagsSatisfied() {
        for (int i = 0; i < flags.length; i++) {
            if (!flags[i]) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Trigger{" + "flags=" + Arrays.toString(flags) + '}';
    }
    
}
