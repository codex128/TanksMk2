/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.Drive;
import codex.tanksmk2.components.Rotation;
import codex.tanksmk2.components.TankMoveDirection;
import codex.tanksmk2.components.TargetMove;
import codex.tanksmk2.components.TurnSpeed;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class TankRotationSystem extends AbstractGameSystem {
    
    private EntityData ed;
    private EntitySet entities;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        entities = ed.getEntities(
            Rotation.class,
            TargetMove.class,
            TurnSpeed.class,
            Drive.class
        );
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        entities.applyChanges();
        for (var e : entities) {
            if (rotateTo(e, e.get(TargetMove.class).getDirection(), time)) {
                ed.setComponents(e.getId(), new TankMoveDirection(getDriveDirection(e)));
            }
            else {
                ed.setComponent(e.getId(), new TankMoveDirection(Vector3f.ZERO));
            }
        }
    }
    
    private boolean rotateTo(Entity e, Vector3f direction, SimTime time) {
        if (direction.equals(Vector3f.ZERO)) {
            return false;
        }
        final float bias = -0.2f;
        final float threshold = FastMath.PI*0.55f;
        Vector3f current = getDriveDirection(e);
        if (current.dot(direction) < bias) {
            e.set(e.get(Drive.class).reverse());
            return false;
        }
        float left = FastMath.sign(direction.cross(Vector3f.UNIT_Y).dot(current));
        float angle = current.angleBetween(direction);
        double turn = e.get(TurnSpeed.class).getSpeed()*time.getTpf();
        if (angle > turn) {
            rotate(e, (float)turn*left, Vector3f.UNIT_Y);
        }
        else {
            //e.set(e.get(EntityTransform.class).setRotation(direction.multLocal(e.get(Drive.class).asNumber()), Vector3f.UNIT_Y));
        }
        return angle < threshold;
    }
    private void rotate(Entity e, float angle, Vector3f axis) {
        e.set(e.get(Rotation.class).rotate(new Quaternion().fromAngleAxis(angle, axis)));
    }
    private Vector3f getDriveDirection(Entity e) {
        return e.get(Rotation.class).getRotation().mult(Vector3f.UNIT_Z).multLocal(e.get(Drive.class).asNumber());
    }
    
}
