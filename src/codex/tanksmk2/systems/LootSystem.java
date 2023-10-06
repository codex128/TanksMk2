/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.CreateLootOnDeath;
import codex.tanksmk2.components.Dead;
import codex.tanksmk2.components.Loot;
import codex.tanksmk2.factories.EntityFactory;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class LootSystem extends AbstractGameSystem {
    
    private EntityData ed;
    private EntitySet entities;

    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        entities = ed.getEntities(Loot.class, CreateLootOnDeath.class, Dead.class);
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        if (entities.applyChanges()) {
            entities.getAddedEntities().forEach(e -> create(e));
        }
    }
    
    private void create(Entity e) {
        getManager().get(EntityFactory.class).createLoot(e.getId(), e.get(Loot.class));
    }
    
}
