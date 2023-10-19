/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.LinearVelocity;
import codex.tanksmk2.components.MatValue;
import codex.tanksmk2.components.Rotation;
import codex.tanksmk2.components.TargetTo;
import codex.tanksmk2.components.Tread;
import codex.tanksmk2.components.Wheel;
import codex.tanksmk2.util.GameUtils;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.shader.VarType;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class WheelSystem extends AbstractGameSystem {
    
    private static final float WHEEL_COEFFICIENT = 1f, TREAD_COEFFICIENT = 0.8f;
    
    private EntityData ed;
    private EntitySet wheels, treads;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        wheels = ed.getEntities(Rotation.class, Wheel.class);
        treads = ed.getEntities(TargetTo.class, Tread.class);
    }
    @Override
    protected void terminate() {
        wheels.release();
        treads.release();
    }
    @Override
    public void update(SimTime time) {
        wheels.applyChanges();
        for (var e : wheels) {
            updateWheel(e, time);
        }
        treads.applyChanges();
        for (var e : treads) {
            updateTread(e, time);
        }
    }
    
    private void updateWheel(Entity e, SimTime time) {
        float angle = getMovement(e, time, WHEEL_COEFFICIENT);
        if (angle != 0f) {
            //var w = e.get(Wheel.class).add(angle*10);
            //System.out.println(w+"  "+angle);
            //e.set(w);
            //e.set(new Rotation(new Quaternion().fromAngles(0f, w.getAngle(), FastMath.HALF_PI)));
        }
    }
    private void updateTread(Entity e, SimTime time) {
        float move = getMovement(e, time, TREAD_COEFFICIENT);
        if (move != 0f) {
            var t = e.get(Tread.class).add(move*-.2f);
            e.set(t);
            ed.setComponent(e.getId(), new MatValue(t.getParam(), VarType.Float, t.getPosition()));
        }
    }
    private float getMovement(Entity e, SimTime time, float coefficient) {
        var velocity = GameUtils.getComponent(ed, e.getId(), LinearVelocity.class);
        if (velocity == null || velocity.getVelocity().lengthSquared() == 0f) {
            return 0f;
        }
        var wTransform = GameUtils.getWorldTransform(ed, e.getId());
        return velocity.getVelocity().length()*coefficient*getRotationFactor(wTransform.getRotation(), velocity)*(float)time.getTpf();
    }
    private float getRotationFactor(Quaternion rotation, LinearVelocity velocity) {
        return rotation.mult(Vector3f.UNIT_Z).dot(velocity.getVelocity().normalize());
    }
    
}
