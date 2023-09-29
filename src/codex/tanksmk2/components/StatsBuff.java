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
public class StatsBuff implements EntityComponent {
    
    private final EntityId target;
    private final boolean removeOnMiss;

    public StatsBuff(EntityId target) {
        this(target, true);
    }
    public StatsBuff(EntityId target, boolean removeOnMiss) {
        this.target = target;
        this.removeOnMiss = removeOnMiss;
    }

    public EntityId getTarget() {
        return target;
    }
    public boolean isRemoveOnMiss() {
        return removeOnMiss;
    }
    @Override
    public String toString() {
        return "StatBuff{" + "target=" + target + '}';
    }
    
}
