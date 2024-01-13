/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.states;

import codex.tanksmk2.ESAppState;
import codex.tanksmk2.collision.Raycaster;
import codex.tanksmk2.components.Bounces;
import codex.tanksmk2.components.LaserEmitter;
import codex.tanksmk2.components.LaserInfo;
import codex.tanksmk2.mesh.Laser;
import codex.tanksmk2.util.GameUtils;
import com.jme3.app.Application;
import com.simsilica.bullet.BulletSystem;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import java.util.HashMap;

/**
 *
 * @author codex
 */
public class LaserState extends ESAppState {

    private EntitySet entities;
    private BulletSystem bullet;
    private Raycaster raycaster;
    private HashMap<EntityId, Laser> lasers = new HashMap<>();
    
    @Override
    protected void init(Application app) {
        bullet = getSystems().get(BulletSystem.class);
        entities = ed.getEntities(LaserEmitter.class, LaserInfo.class, Bounces.class);
        raycaster = new Raycaster();
        raycaster.setSpace(bullet.getSpace());
    }
    @Override
    protected void cleanup(Application app) {
        entities.release();
        lasers.clear();
    }
    @Override
    protected void onEnable() {}
    @Override
    protected void onDisable() {}
    @Override
    public void update(float tpf) {
        if (entities.applyChanges()) {
            entities.getAddedEntities().forEach(e -> create(e));
            entities.getRemovedEntities().forEach(e -> destroy(e));
        }
        for (var e : entities) {
            update(e, tpf);
        }
    }
    
    private void create(Entity e) {        
        var laser = new Laser(e, raycaster);
        laser.loadMaterial(assetManager);
        rootNode.attachChild(laser);
        lasers.put(e.getId(), laser);
    }
    private void destroy(Entity e) {
        lasers.remove(e.getId()).removeFromParent();
    }
    private void update(Entity e, float tpf) {
        var laser = lasers.get(e.getId());
        if (GameUtils.isDead(ed, e.getId())) {
            laser.removeFromParent();
            return;
        }
        laser.cast(ed);
        laser.applyChanges();
        laser.update(tpf);
    }
    
}
