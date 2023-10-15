/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.bullet.TankPhysicsDriver;
import codex.tanksmk2.components.Health;
import codex.tanksmk2.components.Stats;
import codex.tanksmk2.components.TankMoveDirection;
import com.simsilica.bullet.BulletSystem;
import com.simsilica.bullet.Mass;
import com.simsilica.bullet.ShapeInfo;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityContainer;
import com.simsilica.es.EntityData;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class TankDriverSystem extends AbstractGameSystem {
    
    private EntityData ed;
    private BulletSystem bullet;
    private TankContainer container;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        bullet = getSystem(BulletSystem.class, true);
        container = new TankContainer(ed);
        container.start();
    }
    @Override
    protected void terminate() {
        container.stop();
    }
    @Override
    public void update(SimTime time) {
        container.update();
    }
    
    private class TankContainer extends EntityContainer<TankPhysicsDriver> {

        public TankContainer(EntityData ed) {
            super(ed, Health.filter(true), ShapeInfo.class, Mass.class,
                    TankMoveDirection.class, Stats.class, Health.class);
        }
        
        @Override
        protected TankPhysicsDriver addObject(Entity entity) {
            var driver = new TankPhysicsDriver(ed, entity);
            bullet.setControlDriver(entity.getId(), driver);
            return driver;
        }
        @Override
        protected void updateObject(TankPhysicsDriver t, Entity entity) {}
        @Override
        protected void removeObject(TankPhysicsDriver t, Entity entity) {
            bullet.setControlDriver(entity.getId(), null);
        }
        
    }
    
}
