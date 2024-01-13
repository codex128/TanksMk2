/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.collision.Raycaster;
import codex.tanksmk2.collision.SegmentedRaycast;
import codex.tanksmk2.collision.ShapeFilter;
import codex.tanksmk2.collision.VolumeContactSpace;
import codex.tanksmk2.components.*;
import codex.tanksmk2.factories.FactoryInfo;
import codex.tanksmk2.factories.CustomerEntityFactory;
import codex.tanksmk2.util.GameUtils;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.simsilica.bullet.BulletSystem;
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
    
    private EntityData ed;
    private BulletSystem physics;
    private VolumeContactSpace volumeSpace;
    private EntitySet entities;
    private boolean collideBackFaces = !false;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class, true);
        physics = getManager().get(BulletSystem.class, true);
        volumeSpace = getManager().get(VolumeContactSpace.class, true);
        entities = ed.getEntities(Position.class, Direction.class, Speed.class, Bounces.class, CreatedBy.class);
    }
    @Override
    protected void terminate() {
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
        } catch (Exception e) {
            EventBus.publish(ErrorEvent.fatalError, new ErrorEvent(e));
        }
    }
    @Override
    public void physicsTick(PhysicsSpace space, float timeStep) {}
    
    private void update(Entity e, float timeStep) {
        // Either at least one bounce must be made,
        // or the bullet and the shape are not created by the same entity,
        // or the shape did not create the bullet.
        var filter = ShapeFilter.or(
            ShapeFilter.byBounces(1, -1),
            ShapeFilter.nor(ShapeFilter.byCreator(null)),
            ShapeFilter.notId(e.get(CreatedBy.class).getCreatorId())
        );
        var raycaster = new Raycaster();
        raycaster.setSpace(physics.getSpace());
        raycaster.setSpace(volumeSpace);
        var iterator = new SegmentedRaycast(ed, raycaster, e.getId(), constructRay(e), filter).iterator();
        while (iterator.hasNext()) {
            // set probe distance by velocity magnitude
            if (e.get(Speed.class).getSpeed() > 0) {
                iterator.setNextDistance(e.get(Speed.class).getSpeed()*timeStep-iterator.getDistanceTraveled());
                if (iterator.getProbeDistance() <= 0 || (iterator.getNumIterations() > 0
                        && iterator.advanceProbePosition(SegmentedRaycast.ADV_DIST) <= 0)) {
                    break;
                }
            }
            // perform raytest
            iterator.next();
            // process collision results
            if (iterator.collisionOccured()) {
                Vector3f normal = iterator.getClosestResult().getCollisionNormal();
                Vector3f direction = new Vector3f(iterator.getNextDirection());
                // only process collisions on front faces
                if (!collideBackFaces && normal.dot(direction) > 0) {
                    continue;
                }
                var id = iterator.getCollisionEntity();
                boolean impact = ed.getComponent(id, KillBulletOnTouch.class) != null;
                if (!impact) {
                    var reflect = ed.getComponent(id, ReflectOnTouch.class);
                    if (reflect != null) {
                        iterator.setNextDirection(GameUtils.reflect(direction, normal));
                        var b = e.get(Bounces.class);
                        if (reflect.isConsumeBounce()) {
                            e.set(b = b.increment());
                        }
                        impact = b.isExhausted();
                        if (!impact) GameUtils.onComponentExists(ed, e.getId(), ApplyImpulseOnRicochet.class, c -> {                       
                            applyForce(e, id, iterator.getContactPoint(), direction, c.getFactor(), 0.5f); 
                        });
                    }
                }
                if (impact) {
                    var d = ed.getComponent(e.getId(), Damage.class);
                    if (d != null) ed.setComponents(ed.createEntity(),
                        new TargetTo(id), d,
                        GameUtils.duration(getManager().getStepTime(), 1.0)
                    );
                    createImpactEffect(e.getId());
                    createImpactEffect(id);
                    GameUtils.onComponentExists(ed, e.getId(), ApplyImpulseOnImpact.class, c -> {                       
                        applyForce(e, id, iterator.getContactPoint(), direction, c.getFactor(), 0.5f); 
                    });
                    ed.setComponents(ed.createEntity(),
                        new TargetTo(e.getId()),
                        new Damage(Damage.INFINITE),
                        GameUtils.duration(getManager().getStepTime(), 0.5)
                    );
                    break;
                } else {
                    createRicochetEffect(e.getId());
                    createRicochetEffect(id);
                }
            }
        }
        // bullets are assumed to have no parents that affect their world transform
        e.set(new Position(iterator.getContactPoint()));
        e.set(new Direction(iterator.getNextDirection()));
    }
    
    private Ray constructRay(Entity e) {
        return new Ray(e.get(Position.class).getPosition(), e.get(Direction.class).getDirection());
    }
    private void createRicochetEffect(EntityId id) {
        CustomerEntityFactory.create(new FactoryInfo(ed, getManager().getStepTime()),
                CreateOnRicochet.class, id, false);
    }
    private void createImpactEffect(EntityId id) {
        CustomerEntityFactory.create(new FactoryInfo(ed, getManager().getStepTime()),
                CreateOnImpact.class, id, false);
    }
    private void applyForce(Entity bullet, EntityId target, Vector3f position, Vector3f direction, float factor, float blend) {
        var t = GameUtils.getWorldTransform(ed, target);
        ed.setComponents(ed.createEntity(),
            new Force(FastMath.interpolateLinear(blend, direction, GameUtils.directionTo(position, t.getTranslation()))
                    .normalizeLocal().multLocal(bullet.get(Speed.class).getSpeed()*factor)),
            new TargetTo(target),
            GameUtils.duration(getManager().getStepTime(), 0.2)
        );
    }
    
}
