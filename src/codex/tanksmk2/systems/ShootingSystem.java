/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.*;
import codex.tanksmk2.factories.BulletStats;
import codex.tanksmk2.factories.EntityFactory;
import codex.tanksmk2.factories.FactoryInfo;
import codex.tanksmk2.factories.CustomerEntityFactory;
import codex.tanksmk2.util.GameUtils;
import com.jme3.math.Vector3f;
import com.simsilica.es.CreatedBy;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class ShootingSystem extends AbstractGameSystem {
    
    private EntityData ed;
    private EntitySet shooters;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        shooters = ed.getEntities(EquipedGuns.class, Trigger.class, Stats.class, Inventory.class, AmmoChannel.class, Firerate.class);
    }
    @Override
    protected void terminate() {
        shooters.release();
    }
    @Override
    public void update(SimTime time) {
        shooters.applyChanges();
        for (var e : shooters) {
            if (readyToShoot(e, time)) {
                shoot(e, time);
            }
        }
    }
    
    private boolean readyToShoot(Entity e, SimTime time) {
        return !GameUtils.isDead(ed, e.getId())
            && e.get(Trigger.class).isPulled()
            && !e.get(Inventory.class).isExhausted(e.get(AmmoChannel.class).getChannel())
            && e.get(Firerate.class).isComplete(time);
    }
    private void shoot(Entity e, SimTime time) {
        for (var g : e.get(EquipedGuns.class).getGuns()) {
            createBullet(e, g, time);
            CustomerEntityFactory.create(new FactoryInfo(ed, time), CreateOnShoot.class, g, true);
        }
        ed.setComponents(ed.createEntity(), new TargetTo(e.getId()), new Supplier(e.get(AmmoChannel.class).getChannel(), -1));
        e.set(new Firerate(time.getFutureTime(e.get(Stats.class).get(Stats.FIRERATE))));
    }
    private EntityId createBullet(Entity e, EntityId gun, SimTime time) {
        var transform = GameUtils.getWorldTransform(ed, gun);
        var direction = transform.getRotation().mult(Vector3f.UNIT_Z);
        var bullet = switch (e.get(AmmoChannel.class).getChannel()) {
            case Inventory.BULLETS -> EntityFactory.createBullet(new FactoryInfo(ed, time), transform.getTranslation(), direction, BulletStats.BULLET.apply(e.get(Stats.class)));
            case Inventory.MISSILES -> EntityFactory.createMissile(new FactoryInfo(ed, time), transform.getTranslation(), direction, BulletStats.MISSILE.apply(e.get(Stats.class)));
            //case Inventory.GRENADES -> 
            default -> null;
        };
        ed.setComponent(bullet, new CreatedBy(e.getId()));
        return bullet;
    }
    
}
