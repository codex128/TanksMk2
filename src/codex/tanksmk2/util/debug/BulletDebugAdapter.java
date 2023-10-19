/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.util.debug;

import codex.tanksmk2.ESAppState;
import com.jme3.app.Application;
import com.jme3.bullet.debug.BulletDebugAppState;
import com.jme3.bullet.debug.DebugConfiguration;
import com.simsilica.bullet.BulletSystem;
import com.simsilica.event.EventListener;
import com.simsilica.event.EventType;
import com.simsilica.sim.SimEvent;

/**
 *
 * @author codex
 */
public class BulletDebugAdapter extends ESAppState implements EventListener<SimEvent> {
    
    private final DebugConfiguration bulletDebugConfig;
    private BulletDebugAppState bulletDebugApp;
    
    public BulletDebugAdapter(DebugConfiguration bulletDebugConfig) {
        this.bulletDebugConfig = bulletDebugConfig;
    }
    
    @Override
    protected void init(Application app) {
        if (bulletDebugApp != null) {
            getStateManager().attach(bulletDebugApp);
        }
    }
    @Override
    protected void cleanup(Application app) {
        if (bulletDebugApp != null) {
            getStateManager().detach(bulletDebugApp);
        }
    }
    @Override
    protected void onEnable() {}
    @Override
    protected void onDisable() {}
    @Override
    public void update(float tpf) {}
    @Override
    public void newEvent(EventType<SimEvent> type, SimEvent event) {
        if (type == SimEvent.simInitialized) {
            var bullet = event.getManager().get(BulletSystem.class);
            if (bullet != null) {
                bulletDebugConfig.setSpace(bullet.getSpace());
                bulletDebugApp = new BulletDebugAppState(bulletDebugConfig);
                if (isInitialized()) {
                    getStateManager().attach(bulletDebugApp);
                }
            }
        }
    }
    
}
