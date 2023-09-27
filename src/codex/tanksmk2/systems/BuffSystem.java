/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;
import java.util.HashMap;
import codex.tanksmk2.components.DeliveryTarget;
import codex.tanksmk2.components.Stats;

/**
 *
 * @author codex
 */
public class BuffSystem extends AbstractGameSystem {

    private EntityData ed;
    private EntitySet entities;
    private final HashMap<EntityId, Stats> statsMap = new HashMap<>();
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        entities = ed.getEntities(DeliveryTarget.class, Stats.class);
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        if (entities.applyChanges()) {
            for (var e : entities) {
                var buff = e.get(Stats.class);
                var target = e.get(DeliveryTarget.class).getTarget();
                var stats = statsMap.get(target);
                if (stats == null) {
                    stats = new Stats();
                    statsMap.put(target, stats);
                }
                for (int i = 0; i < buff.getValues().length; i++) {
                    stats.set(i, stats.get(i)+buff.get(i));
                }
            }
            statsMap.forEach((id, stats) -> {
                ed.setComponent(id, stats);
            });
            statsMap.clear();
        }
    }
    
}
