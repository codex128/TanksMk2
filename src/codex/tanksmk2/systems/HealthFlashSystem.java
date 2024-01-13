/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.FlashHealthChange;
import codex.tanksmk2.components.GameObject;
import codex.tanksmk2.components.Health;
import codex.tanksmk2.components.HealthFlashData;
import codex.tanksmk2.components.MatValue;
import codex.tanksmk2.components.TargetTo;
import codex.tanksmk2.util.GameUtils;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.shader.VarType;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;
import java.util.HashMap;

/**
 * Makes select models flash when health is taken away.
 * 
 * @author codex
 */
public class HealthFlashSystem extends AbstractGameSystem {
    
    private static final ColorRGBA POS = ColorRGBA.Green, NEG = ColorRGBA.Red;
    
    private EntityData ed;
    private EntitySet targets, controllers;
    private final HashMap<EntityId, EntityId> controlMap = new HashMap<>();
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        targets = ed.getEntities(FlashHealthChange.class, Health.class);
        controllers = ed.getEntities(HealthFlashData.class, MatValue.class, TargetTo.class);
    }
    @Override
    protected void terminate() {
        controlMap.clear();
        targets.release();
        controllers.release();
        // set to null so they cannot be used again
        targets = null;
        controllers = null;
    }
    @Override
    public void update(SimTime time) {
        controllers.applyChanges();
        for (var e : controllers) {
            if (GameUtils.isDead(ed, e.getId())) {
                continue;
            }
            var data = e.get(HealthFlashData.class);
            ColorRGBA color = null;
            double timeLapsed = time.getTimeInSeconds()-data.getStartTime();
            if (timeLapsed >= data.getDuration()) {
                // remove from map so another control entity can be created
                controlMap.remove(e.get(TargetTo.class).getTargetId());
                e.set(GameUtils.duration(time, 1.0));
                GameUtils.kill(ed, e.getId());
                // leave color null, so the shader returns to default behavior
            } else {
                color = new ColorRGBA(data.getColor());
                color.a *= (float)((data.getDuration()-timeLapsed)/data.getDuration());
            }
            e.set(new MatValue(e.get(MatValue.class).getName(), VarType.Vector4, color));
        }
        if (targets.applyChanges()) for (var e : targets.getChangedEntities()) {
            var h = e.get(Health.class);
            if (h.getHealthGain() == 0) {
                continue;
            }
            // check for pre-existing control entity
            var control = controlMap.get(e.getId());
            if (control == null) {
                control = ed.createEntity();
                controlMap.put(e.getId(), control);
            }
            ColorRGBA color;
            if (h.getHealthGain() > 0) {
                color = POS.clone();
            } else {
                color = NEG.clone();
            }
            var c = e.get(FlashHealthChange.class);
            color.a = FastMath.abs(h.getPercentGain())*c.getIntensity();
            ed.setComponents(control,
                new GameObject("HealthFlashController"),
                new HealthFlashData(color, time.getTimeInSeconds(), 0.5),
                new MatValue(c.getMatParam(), VarType.Vector4, color),
                new TargetTo(e.getId())
            );
        }
    }
    
}
