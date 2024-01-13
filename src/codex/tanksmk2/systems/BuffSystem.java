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
import codex.tanksmk2.components.Stats;
import codex.tanksmk2.components.TargetTo;
import codex.tanksmk2.util.GameUtils;
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
        entities = ed.getEntities(TargetTo.class, Stats.class);
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        if (entities.applyChanges()) {
            for (var e : entities) {
                var target = e.get(TargetTo.class);
                if (ed.getComponent(target.getTargetId(), Stats.class) == null) {
                    // this is important for two reasons:
                    // 1) Buffs can be applied indiscriminantly, even if entities don't end up needing them
                    // 2) So that dead entities are not accidently revived
                    //if (target.isRemoveOnMiss()) {
                        ed.setComponents(e.getId(), new Decay(time.getTime()));
                    //}
                    continue;
                }
                var buff = e.get(Stats.class);
                var stats = statsMap.get(target.getTargetId());
                if (stats == null) {
                    stats = new Stats();
                    statsMap.put(target.getTargetId(), stats);
                }
                stats.addLocal(buff);
            }
            statsMap.forEach((id, stats) -> {
                ed.setComponent(id, stats);
            });
            statsMap.clear();
        }
    }
    
}
