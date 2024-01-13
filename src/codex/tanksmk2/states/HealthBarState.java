/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.states;

import codex.tanksmk2.ESAppState;
import codex.tanksmk2.components.Health;
import codex.tanksmk2.components.HealthBar;
import codex.tanksmk2.util.GameUtils;
import com.jme3.app.Application;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.control.BillboardControl;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import com.simsilica.lemur.ProgressBar;
import java.util.HashMap;

/**
 * State for placing health bars above entities.
 * 
 * @author codex
 */
public class HealthBarState extends ESAppState {
    
    private EntitySet entities;
    private final Transform transform = new Transform();
    private final HashMap<EntityId, ProgressBar> bars = new HashMap<>();
    
    @Override
    protected void init(Application app) {
        entities = ed.getEntities(Health.class, HealthBar.class);
    }
    @Override
    protected void cleanup(Application app) {
        entities.release();
    }
    @Override
    protected void onEnable() {}
    @Override
    protected void onDisable() {}    
    @Override
    public void update(float tpf) {
        if (entities.applyChanges()) {
            entities.getAddedEntities().forEach(e -> setup(e));
            entities.getRemovedEntities().forEach(e -> destroy(e));
        }
        for (var e : entities) {
            persistentUpdate(e);
        }
    }
    
    private void setup(Entity e) {
        var h = e.get(Health.class);
        var d = e.get(HealthBar.class);
        var bar = new ProgressBar();
        bar.getModel().setMaximum(h.getMaxHealth());
        bar.getModel().setMinimum(0);
        bar.getModel().setValue(h.getHealth());
        bar.setSize(new Vector3f(d.getLength(), d.getThickness(), 0));
        bar.setMessage("_");
        bar.getLabel().setFontSize(0.001f);
        bar.setBackground(null);
        bar.addControl(new BillboardControl());
        rootNode.attachChild(bar);
        bars.put(e.getId(), bar);
    }
    private void persistentUpdate(Entity e) {
        var bar = bars.get(e.getId());
        bar.getModel().setValue(e.get(Health.class).getHealth());
        GameUtils.getWorldTransform(ed, e.getId(), transform);
        transform.getTranslation().addLocal(0, e.get(HealthBar.class).getOffset(), 0);
        bar.setLocalTransform(transform);
    }
    private void destroy(Entity e) {
        var bar = bars.remove(e.getId());
        bar.removeFromParent();
    }
    
}
