/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.collision.SegmentedRaycast;
import codex.tanksmk2.components.BasicTurret;
import codex.tanksmk2.components.EquipedGuns;
import codex.tanksmk2.components.KillBulletOnTouch;
import codex.tanksmk2.components.ReflectOnTouch;
import codex.tanksmk2.components.Rotation;
import codex.tanksmk2.components.Stats;
import codex.tanksmk2.components.Team;
import codex.tanksmk2.components.TriggerPull;
import codex.tanksmk2.util.GameUtils;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.simsilica.bullet.BulletSystem;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class BasicTurretSystem extends AbstractGameSystem implements PhysicsTickListener {

    private EntityData ed;
    private BulletSystem bullet;
    private EntitySet turrets, targets;
    private Transform transform = new Transform();
    private Vector3f normal = new Vector3f();
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        bullet = getManager().get(BulletSystem.class);
        bullet.getSpace().addTickListener(this);
        turrets = ed.getEntities(
            BasicTurret.class,
            EquipedGuns.class,
            Stats.class,
            TriggerPull.class,
            Team.class
        );
        targets = ed.getEntities(Team.class);
    }
    @Override
    protected void terminate() {
        turrets.release();
        targets.release();
        bullet.getSpace().removeTickListener(this);
    }
    @Override
    public void update(SimTime time) {}    
    @Override
    public void prePhysicsTick(PhysicsSpace space, float timeStep) {
        turrets.applyChanges();
        targets.applyChanges();
        for (var e : turrets) {
            var b = e.get(BasicTurret.class);
            for (var t : b.getTurrets()) {
                ed.setComponent(t, ed.getComponent(t, Rotation.class).rotate(
                        new Quaternion().fromAngleAxis(b.getSpeed()*timeStep, Vector3f.UNIT_Y)));
            }
            boolean pull = raytest(space, e);
            if (pull != e.get(TriggerPull.class).isPulled()) {
                ed.setComponent(e.getId(), new TriggerPull(pull));
            }
        }
    }
    @Override
    public void physicsTick(PhysicsSpace space, float timeStep) {}
    
    private boolean raytest(PhysicsSpace space, Entity turret) {
        for (var g : turret.get(EquipedGuns.class).getGuns()) {
            var iterator = new SegmentedRaycast(space, ed, turret.getId(),
                    GameUtils.getRayFromTransform(ed, g, transform)).iterator();
            while (iterator.hasNext()) {
                iterator.next();
                if (iterator.collisionOccured()) {
                    var id = iterator.getCollisionObject().getId();
                    if (iterator.getClosestResult().getHitNormalLocal(null).dot(iterator.getNextDirection()) < -0.5f) {
                        var targetEntity = targets.getEntity(id);
                        if (targetEntity != null && !targetEntity.get(Team.class).equals(turret.get(Team.class))) {
                            return true;
                        }
                    }
                    if (ed.getComponent(id, KillBulletOnTouch.class) != null) {
                        break;
                    }
                    if (ed.getComponent(id, ReflectOnTouch.class) != null) {
                        iterator.getClosestResult().getHitNormalLocal(normal);
                        iterator.setNextDirection(GameUtils.reflect(iterator.getNextDirection(), normal));
                    }
                }
                if (iterator.getNumIterations()-1 > turret.get(Stats.class).get(Stats.BOUNCES)) {
                    break;
                }
            }
        }
        return false;
    }
    
}
