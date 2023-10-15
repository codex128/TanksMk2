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
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.simsilica.bullet.Mass;
import com.simsilica.bullet.ShapeInfo;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class ShockwaveSystem extends AbstractGameSystem {
    
    private EntityData ed;
    private EntitySet shocks;
    private EntitySet shapes;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        shocks = ed.getEntities(Shockwave.class, Position.class);
        shapes = ed.getEntities(ShapeInfo.class, Mass.class, Position.class);
    }
    @Override
    protected void terminate() {
        shocks.release();
        shapes.release();
    }
    @Override
    public void update(SimTime time) {
        if (shocks.applyChanges() && !shocks.isEmpty()) {
            shapes.applyChanges();
            for (var e : shocks) {
                apply(e, time);
            }
        }
    }
    
    private void apply(Entity e, SimTime time) {
        Shockwave shock = e.get(Shockwave.class);
        Vector3f shockPos = e.get(Position.class).getPosition();
        Vector3f diff = new Vector3f();
        for (var s : shapes) {
            diff.set(s.get(Position.class).getPosition()).subtractLocal(shockPos);
            float p = diff.length()/shock.getFalloff();
            if (p < 1) {
                // apply interpolated power
                ed.setComponents(ed.createEntity(),
                    new TargetTo(s.getId()),
                    new Force(diff.normalizeLocal().multLocal(FastMath.interpolateLinear(p, shock.getPower(), 0f))),
                    GameUtils.duration(time, .2)
                );
            }
        }
        // consume shockwave component
        ed.removeComponent(e.getId(), Shockwave.class);
    }
    
}
