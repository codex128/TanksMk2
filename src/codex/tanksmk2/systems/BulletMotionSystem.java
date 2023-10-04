/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.collision.SegmentedRaytest;
import codex.tanksmk2.components.ApplyDamageOnImpact;
import codex.tanksmk2.components.Bounces;
import codex.tanksmk2.components.CreateOnTouch;
import codex.tanksmk2.components.Defunct;
import codex.tanksmk2.components.Direction;
import codex.tanksmk2.components.KillBulletOnTouch;
import codex.tanksmk2.components.Position;
import codex.tanksmk2.components.ReflectOnTouch;
import codex.tanksmk2.components.Speed;
import codex.tanksmk2.components.TargetTo;
import codex.tanksmk2.util.GameUtils;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Ray;
import com.simsilica.bullet.BulletSystem;
import com.simsilica.bullet.EntityPhysicsObject;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.es.common.Decay;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class BulletMotionSystem extends AbstractGameSystem implements PhysicsTickListener {

    private EntityData ed;
    private BulletSystem bullet;
    private EntitySet entities;
    private long lastUpdateTime = 0;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        bullet = getManager().get(BulletSystem.class);
        bullet.getSpace().addTickListener(this);
        entities = ed.getEntities(Position.class, Direction.class, Speed.class, Bounces.class);
    }
    @Override
    protected void terminate() {
        bullet.getSpace().removeTickListener(this);
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        lastUpdateTime = time.getTime();
    }
    @Override
    public void prePhysicsTick(PhysicsSpace space, float timeStep) {
        entities.applyChanges();
        for (var e : entities) {
            update(e);
        }
    }
    @Override
    public void physicsTick(PhysicsSpace space, float timeStep) {}
    
    private void update(Entity e) {
        var iterator = new SegmentedRaytest(bullet.getSpace(), constructRay(e)).iterator();
        while (iterator.hasNext()) {
            // set probe distance by velocity magnitude
            float distance = e.get(Speed.class).getSpeed()-iterator.getDistanceTraveled();
            if (distance <= 0) break;
            iterator.setNextProbeDistance(distance);
            // perform raytest
            iterator.next();
            // process collision results
            if (iterator.collisionOccured() && iterator.getClosestResult().getCollisionObject() instanceof EntityPhysicsObject) {
                var object = (EntityPhysicsObject)iterator.getClosestResult().getCollisionObject();
                if (ed.getComponent(object.getId(), KillBulletOnTouch.class) != null) {
                    // The Defunct component indicates that the entity is "dead."
                    // This should not be confused with "to be removed," but in most
                    // cases, this component will lead to the entity being removed.
                    // Think of Defunct as a motion to get rid of the component, with
                    // other systems agreeing or disagreeing.
                    ed.setComponent(e.getId(), new Defunct());
                    break;
                }
                else {
                    var reflect = ed.getComponent(object.getId(), ReflectOnTouch.class);
                    if (reflect != null) {
                        iterator.setNextDirection(GameUtils.ricochet(iterator.getNextDirection(), iterator.getClosestResult().getHitNormalLocal(null)));
                        if (reflect.isForce()) {
                            e.set(e.get(Bounces.class).increment());
                        }
                    }
                    if (e.get(Bounces.class).isExhausted()) {
                        ed.setComponent(e.getId(), new Defunct());
                        break;
                    }
                }
                var applyDamage = ed.getComponent(object.getId(), ApplyDamageOnImpact.class);
                if (applyDamage != null) {
                    for (var t : applyDamage.getDamageType()) {
                        var d = ed.getComponent(e.getId(), t);
                        if (d != null) {
                            ed.setComponents(ed.createEntity(), new TargetTo(object.getId()), d, Decay.duration(lastUpdateTime, applyDamage.getDuration()));
                        }
                    }
                }
                var create = ed.getComponent(object.getId(), CreateOnTouch.class);
                if (create != null) {
                    
                }
            }
        }
        e.set(new Position(iterator.getContactPoint()));
        e.set(new Direction(iterator.getNextDirection()));
    }
    private Ray constructRay(Entity e) {
        return new Ray(e.get(Position.class).getPosition(), e.get(Direction.class).getDirection());
    }
    
}
