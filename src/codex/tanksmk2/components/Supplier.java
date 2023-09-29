/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class Supplier implements EntityComponent {
    
    private final EntityId target;
    private final int channel;
    private final int amount;

    public Supplier(EntityId target, int channel, int amount) {
        this.target = target;
        this.channel = channel;
        this.amount = amount;
    }

    public EntityId getTarget() {
        return target;
    }
    public int getChannel() {
        return channel;
    }
    public int getAmount() {
        return amount;
    }
    public boolean isReplenish() {
        return amount < 0;
    }
    
}
