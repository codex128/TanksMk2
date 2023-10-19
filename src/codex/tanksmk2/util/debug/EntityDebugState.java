/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.util.debug;

import codex.tanksmk2.ESAppState;
import codex.tanksmk2.util.GameUtils;
import com.jme3.app.Application;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

/**
 *
 * @author codex
 */
public class EntityDebugState extends ESAppState implements ActionListener {
    
    private static final float PRINT_FREQUENCY = 1f;
    
    private EntitySet debugChanges, debugTransform;
    private boolean printEnabled = false;
    private float timeSinceUpdate = 0f;
    
    @Override
    protected void init(Application app) {
        
        debugChanges = ed.getEntities(ObserveChangesOf.class);
        debugTransform = ed.getEntities(ObserveTransform.class);
        
        inputManager.addMapping("debug", new KeyTrigger(KeyInput.KEY_F2));
        inputManager.addListener(this, "debug");
        
    }
    @Override
    protected void cleanup(Application app) {
        debugChanges.release();
        debugTransform.release();
        inputManager.removeListener(this);
    }
    @Override
    protected void onEnable() {}
    @Override
    protected void onDisable() {}    
    @Override
    public void update(float tpf) {
        if (printEnabled && (timeSinceUpdate += tpf) >= PRINT_FREQUENCY) {
            if (debugChanges.applyChanges()) {
                System.out.println("------ CHANGES ------");
                debugChanges.getAddedEntities().forEach(e -> debugChange(e));
                debugChanges.getChangedEntities().forEach(e -> debugChange(e));
            }
            debugTransform.applyChanges();
            if (!debugTransform.isEmpty()) {
                System.out.println("------ TRANSFORMS ------");
                for (var e : debugTransform) {
                    var t = GameUtils.getWorldTransform(ed, e.getId());
                    System.out.println(
                            "  "+GameUtils.getGameObject(ed, e.getId())+" = {"
                         +"\n    translation = "+t.getTranslation()
                         +"\n    rotation    = "+t.getRotation()
                         +"\n  }");
                }
            }
            timeSinceUpdate = 0f;
        }
    }
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed) {
            printEnabled = !printEnabled;
        }
    }
    
    private void debugChange(Entity e) {
        System.out.println("  "+GameUtils.getGameObject(ed, e.getId()));
        for (var t : e.get(ObserveChangesOf.class).getComponents()) {
            System.out.println("    "+ed.getComponent(e.getId(), t));
        }
    }
    
}
