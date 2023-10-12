/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.collision.SegmentedRaytest;
import codex.tanksmk2.components.ApplyImpulseOnImpact;
import codex.tanksmk2.components.Bounces;
import codex.tanksmk2.components.CreateEffectOnImpact;
import codex.tanksmk2.components.CreateEffectOnRicochet;
import codex.tanksmk2.components.Damage;
import codex.tanksmk2.components.Dead;
import codex.tanksmk2.components.Direction;
import codex.tanksmk2.components.KillBulletOnTouch;
import codex.tanksmk2.components.Position;
import codex.tanksmk2.components.ReflectOnTouch;
import codex.tanksmk2.components.Speed;
import codex.tanksmk2.components.TargetTo;
import codex.tanksmk2.util.GameUtils;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.math.Ray;
import com.simsilica.bullet.BulletSystem;
import com.simsilica.bullet.EntityPhysicsObject;
import com.simsilica.bullet.Impulse;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
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
    public void update(SimTime time) {}
    @Override
    public void prePhysicsTick(PhysicsSpace space, float timeStep) {
        try {
            entities.applyChanges();
            for (var e : entities) {
                if (GameUtils.isDefunct(ed, e.getId())) {
                    continue;
                }
                update(e, timeStep);
            }
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
    @Override
    public void physicsTick(PhysicsSpace space, float timeStep) {}
    
    private void update(Entity e, float timeStep) {
        var iterator = new SegmentedRaytest(bullet.getSpace(), constructRay(e)).iterator();
        while (iterator.hasNext()) {
            // set probe distance by velocity magnitude
            if (e.get(Speed.class).getSpeed() > 0) {
                float distance = e.get(Speed.class).getSpeed()*timeStep-iterator.getDistanceTraveled();
                if (distance <= 0) break;
                iterator.setNextProbeDistance(distance);
            }
            // perform raytest
            iterator.next();
            // process collision results
            if (iterator.collisionOccured() && iterator.getClosestResult().getCollisionObject() instanceof EntityPhysicsObject) {
                boolean impact = false;
                var object = (EntityPhysicsObject)iterator.getClosestResult().getCollisionObject();
                if (ed.getComponent(object.getId(), KillBulletOnTouch.class) != null) {
                    impact = true;
                }
                else {
                    var reflect = ed.getComponent(object.getId(), ReflectOnTouch.class);
                    if (reflect != null) {
                        iterator.setNextDirection(GameUtils.ricochet(iterator.getNextDirection(), iterator.getClosestResult().getHitNormalLocal(null)));
                        if (reflect.isConsumeBounce()) {
                            e.set(e.get(Bounces.class).increment());
                        }
                    }
                    if (e.get(Bounces.class).isExhausted()) {
                        impact = true;
                    }
                }
                if (impact) {
                    var d = ed.getComponent(e.getId(), Damage.class);
                    if (d != null) {
                        ed.setComponents(ed.createEntity(), new TargetTo(object.getId()), d,
                                Decay.duration(getManager().getStepTime().getTime(), getManager().getStepTime().toSimTime(0.1)));
                    }
                    createImpactEffect(e.getId());
                    createImpactEffect(object.getId());
                    var impulse = ed.getComponent(e.getId(), ApplyImpulseOnImpact.class);
                    if (impulse != null) {
                        ed.setComponent(object.getId(), new Impulse(iterator.getNextDirection().mult(e.get(Speed.class).getSpeed())));
                    }
                    ed.setComponent(e.getId(), new Dead());
                    break;
                }
                else {
                    createRicochetEffect(e.getId());
                    createRicochetEffect(object.getId());
                }
            }
        }
        System.out.println("position="+iterator.getContactPoint()+", direction="+iterator.getNextDirection());
        e.set(new Position(iterator.getContactPoint()));
        e.set(new Direction(iterator.getNextDirection()));
    }
    private Ray constructRay(Entity e) {
        return new Ray(e.get(Position.class).getPosition(), e.get(Direction.class).getDirection());
    }
    
    private void createRicochetEffect(EntityId id) {
        var create = ed.getComponent(id, CreateEffectOnRicochet.class);
        if (create != null) {
            
        }
    }
    private void createImpactEffect(EntityId id) {
        var create = ed.getComponent(id, CreateEffectOnImpact.class);
        if (create != null) {
            
        }
    }
    
}
