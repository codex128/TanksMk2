/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.states;

import codex.tanksmk2.ESAppState;
import codex.tanksmk2.components.Emission;
import codex.tanksmk2.components.EntityParticleEffect;
import codex.tanksmk2.factories.FactoryInfo;
import codex.tanksmk2.factories.ParticleEffectFactory;
import codex.tanksmk2.util.GameUtils;
import codex.vfx.particles.ParticleData;
import codex.vfx.particles.ParticleGroup;
import com.jme3.app.Application;
import com.jme3.math.Transform;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Logger;
import codex.tanksmk2.factories.LegacyFactory;

/**
 *
 * @author codex
 */
public class ParticleState extends ESAppState {

    private static final Logger LOG = Logger.getLogger(ParticleState.class.getName());
    
    private EntitySet effects, emissions;
    private LegacyFactory<ParticleGroup> factory;
    private FactoryInfo factoryInfo;
    private final HashMap<Object, ParticleGroup> groups = new HashMap<>();
    private final LinkedList<ParticleGroup> dying = new LinkedList<>();
    private final Transform transform = new Transform();
    
    @Override
    protected void init(Application app) {
        effects = ed.getEntities(EntityParticleEffect.class);
        emissions = ed.getEntities(Emission.class);
        if (factory == null) {
            factory = new ParticleEffectFactory(assetManager);
        }
        factoryInfo = new FactoryInfo(ed, app);
    }
    @Override
    protected void cleanup(Application app) {
        for (var g : groups.values()) {
            g.removeFromParent();
        }
        for (var g : dying) {
            g.removeFromParent();
        }
        groups.clear();
        dying.clear();
        effects.release();
        emissions.release();
    }
    @Override
    protected void onEnable() {}
    @Override
    protected void onDisable() {}
    @Override
    public void update(float tpf) {
        if (effects.applyChanges()) {
            effects.getAddedEntities().forEach(e -> setupEffect(e));
            effects.getChangedEntities().forEach(e -> destroyEffect(e));
        }
        for (var e : effects) {
            updateEffect(e);
        }
        if (!dying.isEmpty()) for (var it = dying.iterator(); it.hasNext();) {
            var g = it.next();
            if (g.isEmpty()) {
                it.remove();
                g.removeFromParent();
            }
        }
        if (emissions.applyChanges()) {
            emissions.getAddedEntities().forEach(e -> createEmission(e));
        }
    }
    
    private void setupEffect(Entity e) {
        var effect = e.get(EntityParticleEffect.class);
        Object key = effect.getKey(e.getId());
        var g = groups.get(key);
        if (g == null) {
            factoryInfo.setPrefab(effect.getPrefab());
            factoryInfo.setCustomer(e.getId());
            g = factory.load(factoryInfo);
            groups.put(key, g);
            rootNode.attachChild(g);
        }
        if (!effect.isControlling()) {
            ed.removeComponent(e.getId(), EntityParticleEffect.class);
        }
    }
    private void updateEffect(Entity e) {
        var effect = e.get(EntityParticleEffect.class);
        if (effect.isControlling()) {
            GameUtils.getWorldTransform(ed, e.getId(), transform);
            var g = groups.get(effect.getKey(e.getId()));
            g.setLocalTranslation(transform.getTranslation());
        }
    }
    private void destroyEffect(Entity e) {
        var effect = e.get(EntityParticleEffect.class);
        if (effect.isControlling()) {
            var g = groups.remove(effect.getKey(e.getId()));
            if (g != null) {
                dying.add(g);
            }
        }
    }
    
    private void createEmission(Entity e) {
        var emission = e.get(Emission.class);
        var g = groups.get(emission.getKey(e.getId()));
        if (g != null) for (int i = 0; i < emission.getNumParticles(); i++) {
            var p = new ParticleData();
            positionParticle(e.getId(), g, p, emission.getPosition());
            g.add(p);
        }
    }    
    private void positionParticle(EntityId id, ParticleGroup g, ParticleData p, int position) {
        switch (position) {
            case Emission.ENTITY -> {
                GameUtils.getWorldTransform(ed, id, transform);
                p.setPosition(transform.getTranslation());
            }
            case Emission.GROUP -> {
                p.setPosition(g.getVolume().getNextPosition(g.getWorldTransform()));
            }
            case Emission.ENTITY_VOLUME -> {
                GameUtils.getWorldTransform(ed, id, transform);
                p.setPosition(g.getVolume().getNextPosition(transform));
            }
        }
    }
    
}
