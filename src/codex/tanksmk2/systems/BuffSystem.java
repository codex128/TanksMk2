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
import codex.tanksmk2.components.StatsBuff;
import codex.tanksmk2.components.Stats;
import com.simsilica.es.common.Decay;

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
        System.out.println("initialize buff system");
        ed = getManager().get(EntityData.class);
        entities = ed.getEntities(StatsBuff.class, Stats.class);
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        if (entities.applyChanges()) {
            for (var e : entities) {
                var target = e.get(StatsBuff.class);
                if (ed.getComponent(target.getTarget(), Stats.class) == null) {
                    // this is important for two reasons:
                    // 1) Buffs can be applied indiscriminantly, even if entities don't end up needing them
                    // 2) So that dead entities are not accidently revived
                    if (target.isRemoveOnMiss()) {
                        ed.setComponents(e.getId(), new Decay(time.getTime()));
                    }
                    continue;
                }
                var buff = e.get(Stats.class);
                var stats = statsMap.get(target.getTarget());
                if (stats == null) {
                    stats = new Stats();
                    statsMap.put(target.getTarget(), stats);
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
