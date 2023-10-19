/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.Force;
import codex.tanksmk2.components.Position;
import codex.tanksmk2.components.Shockwave;
import codex.tanksmk2.components.TargetTo;
import codex.tanksmk2.util.GameUtils;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsRayTestResult;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.simsilica.bullet.BulletSystem;
import com.simsilica.bullet.EntityPhysicsObject;
import com.simsilica.bullet.Mass;
import com.simsilica.bullet.ShapeInfo;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;
import java.util.List;

/**
 *
 * @author codex
 */
public class ShockwaveSystem extends AbstractGameSystem implements PhysicsTickListener {
    
    private EntityData ed;
    private BulletSystem bullet;
    private EntitySet shocks, shapes;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        bullet = getManager().get(BulletSystem.class);
        bullet.getSpace().addTickListener(this);
        shocks = ed.getEntities(Shockwave.class, Position.class);
        shapes = ed.getEntities(ShapeInfo.class, Mass.class, Position.class);
    }
    @Override
    protected void terminate() {
        bullet.getSpace().removeTickListener(this);
        shocks.release();
        shapes.release();
    }
    @Override
    public void update(SimTime time) {
        
    }
    @Override
    public void prePhysicsTick(PhysicsSpace space, float timeStep) {
        if (shocks.applyChanges() && !shocks.isEmpty()) {
            shapes.applyChanges();
            for (var e : shocks) {
                apply(e, space);
            }
        }
    }
    @Override
    public void physicsTick(PhysicsSpace space, float timeStep) {}
    
    private void apply(Entity e, PhysicsSpace space) {
        Shockwave shock = e.get(Shockwave.class);
        Vector3f shockPos = e.get(Position.class).getPosition();
        Vector3f shapePos = new Vector3f();
        Vector3f diff = new Vector3f();
        for (var s : shapes) {
            shapePos.set(s.get(Position.class).getPosition());
            // raytest for objects in between the shock and the physics object
            List<PhysicsRayTestResult> results = space.rayTest(shockPos, shapePos);
            PhysicsRayTestResult closest = null;
            for (var r : results) if (closest == null || r.getHitFraction() < closest.getHitFraction()) {
                closest = r;                
            }
            if (closest == null || (closest.getCollisionObject() instanceof EntityPhysicsObject
                    && ((EntityPhysicsObject)closest.getCollisionObject()).getId().equals(s.getId()))) {
                diff.set(shapePos).subtractLocal(shockPos);
                float p = diff.length()/shock.getFalloff();
                if (diff.equals(Vector3f.ZERO)) {
                    GameUtils.randomUnitVector(diff);
                }
                if (p < 1) {
                    // apply interpolated power
                    ed.setComponents(ed.createEntity(),
                        new TargetTo(s.getId()),
                        new Force(diff.normalizeLocal().multLocal(FastMath.interpolateLinear(p, shock.getPower(), 0f))),
                        GameUtils.duration(getManager().getStepTime(), .2)
                    );
                }
            }
        }
        // consume shockwave component
        ed.removeComponent(e.getId(), Shockwave.class);
    }
    
}
