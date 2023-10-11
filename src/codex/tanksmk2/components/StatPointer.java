/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class StatPointer implements EntityComponent {
    
    private final EntityId holder;
    private final int channel;

    public StatPointer(EntityId holder, int channel) {
        this.holder = holder;
        this.channel = channel;
    }

    public EntityId getHolder() {
        return holder;
    }
    public int getChannel() {
        return channel;
    }
    public float getValue(EntityData ed) {
        var stats = ed.getComponent(holder, Stats.class);
        if (stats == null) return Stats.getDefault(channel);
        return stats.get(channel);
    }
    @Override
    public String toString() {
        return "StatPointer{" + "holder=" + holder + ", channel=" + channel + '}';
    }
    
}
