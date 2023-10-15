/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.collision.SegmentedRaytest;
import codex.tanksmk2.collision.ShapeFilter;
import codex.tanksmk2.components.*;
import codex.tanksmk2.factories.FactoryInfo;
import codex.tanksmk2.factories.CustomerEntityFactory;
import codex.tanksmk2.util.GameUtils;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.simsilica.bullet.BulletSystem;
import com.simsilica.bullet.EntityPhysicsObject;
import com.simsilica.es.CreatedBy;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import com.simsilica.event.ErrorEvent;
import com.simsilica.event.EventBus;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class BulletMotionSystem extends AbstractGameSystem implements PhysicsTickListener {
    
    // either the bullet has bounced at least once, or the shape has a different creator
    private static final ShapeFilter FILTER = ShapeFilter.or(ShapeFilter.byBounces(1, -1), ShapeFilter.nor(ShapeFilter.byCreator(null)));
    
    private EntityData ed;
    private BulletSystem bullet;
    private EntitySet entities;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        bullet = getManager().get(BulletSystem.class);
        bullet.getSpace().addTickListener(this);
        entities = ed.getEntities(Position.class, Direction.class, Speed.class, Bounces.class, CreatedBy.class);
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
                if (GameUtils.isDead(ed, e.getId())) {
                    continue;
                }
                update(e, timeStep);
            }
        }
        catch (Exception e) {
            EventBus.publish(ErrorEvent.fatalError, new ErrorEvent(e));
        }
    }
    @Override
    public void physicsTick(PhysicsSpace space, float timeStep) {}
    
    private void update(Entity e, float timeStep) {
        // either at least one bounce must be made, or the bullet and the shape are not created by the same entity, or the shape did not create the bullet
        var filter = ShapeFilter.or(ShapeFilter.byBounces(1, -1), ShapeFilter.nor(ShapeFilter.byCreator(null)), ShapeFilter.notId(e.get(CreatedBy.class).getCreatorId()));
        var iterator = new SegmentedRaytest(bullet.getSpace(), ed, e.getId(), constructRay(e), filter).iterator();
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
                Vector3f normal = iterator.getClosestResult().getHitNormalLocal(null);
                Vector3f direction = new Vector3f(iterator.getNextDirection());
                // only process collisions on front faces
                if (normal.dot(iterator.getNextDirection()) > 0) {
                    continue;
                }
                boolean impact = false;
                var object = (EntityPhysicsObject)iterator.getClosestResult().getCollisionObject();
                if (ed.getComponent(object.getId(), KillBulletOnTouch.class) != null) {
                    impact = true;
                }
                else {
                    var reflect = ed.getComponent(object.getId(), ReflectOnTouch.class);
                    if (reflect != null) {
                        iterator.setNextDirection(GameUtils.ricochet(direction, normal));
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
                        ed.setComponents(ed.createEntity(),
                            new TargetTo(object.getId()), d,
                            GameUtils.duration(getManager().getStepTime(), 0.1)
                        );
                    }
                    createImpactEffect(e.getId());
                    createImpactEffect(object.getId());
                    applyForce(e, object.getId(), direction);
                    // Create a damager entity that instantly kills the bullet.
                    // Alternatively, if we ever add penetration, a finite damage
                    // value could be applied to allow bullets to penetrate things.
                    ed.setComponents(ed.createEntity(),
                        new TargetTo(e.getId()),
                        new Damage(Damage.INFINITE),
                        GameUtils.duration(getManager().getStepTime(), 0.8)
                    );
                    break;
                }
                else {
                    createRicochetEffect(e.getId());
                    createRicochetEffect(object.getId());
                }
            }
        }
        e.set(new Position(iterator.getContactPoint()));
        e.set(new Direction(iterator.getNextDirection()));
    }
    
    private Ray constructRay(Entity e) {
        return new Ray(e.get(Position.class).getPosition(), e.get(Direction.class).getDirection());
    }
    private void createRicochetEffect(EntityId id) {
        CustomerEntityFactory.create(new FactoryInfo(ed, getManager().getStepTime()), CreateOnRicochet.class, id, false);
    }
    private void createImpactEffect(EntityId id) {
        CustomerEntityFactory.create(new FactoryInfo(ed, getManager().getStepTime()), CreateOnImpact.class, id, false);
    }
    private void applyForce(Entity bullet, EntityId target, Vector3f direction) {
        var impulse = ed.getComponent(bullet.getId(), ApplyImpulseOnImpact.class);
        if (impulse != null) {
            ed.setComponents(ed.createEntity(),
                new Force(direction.mult(bullet.get(Speed.class).getSpeed()*impulse.getFactor())),
                new TargetTo(target),
                GameUtils.duration(getManager().getStepTime(), 0.2)
            );
        }
    }
    
}
