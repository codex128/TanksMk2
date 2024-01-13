/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.states;

import codex.tanksmk2.ESAppState;
import codex.tanksmk2.components.EntityLight;
import codex.tanksmk2.components.InfluenceCone;
import codex.tanksmk2.components.LightProbeSource;
import codex.tanksmk2.components.Position;
import codex.tanksmk2.components.Power;
import codex.tanksmk2.util.FunctionFilter;
import codex.tanksmk2.util.GameEntityContainer;
import codex.tanksmk2.util.GameUtils;
import codex.vfx.utils.VfxUtils;
import com.jme3.app.Application;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.light.LightProbe;
import com.jme3.light.PointLight;
import com.jme3.light.SpotLight;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import java.util.ArrayList;

/**
 *
 * @author codex
 */
public class LightingState extends ESAppState {
    
    private final ArrayList<GameEntityContainer<? extends Light>> entities = new ArrayList<>();
    
    @Override
    protected void init(Application app) {
        entities.add(new DirectionalContainer(ed));
        entities.add(new PointContainer(ed));
        entities.add(new SpotContainer(ed));
        entities.add(new AmbientContainer(ed));
        entities.add(new ProbeContainer(ed));
        entities.forEach(e -> e.start());
    }
    @Override
    protected void cleanup(Application app) {
        entities.forEach(e -> e.stop());
        entities.clear();
    }
    @Override
    protected void onEnable() {}
    @Override
    protected void onDisable() {}
    @Override
    public void update(float tpf) {
        for (var c : entities) {
            c.update();
        }
    }
    
    public Light getLight(EntityId id) {
        for (var container : entities) {
            var l = container.getObject(id);
            if (l != null) return l;
        }
        return null;
    }
    public <T extends Light> T getLight(EntityId id, Class<T> type) {
        var l = getLight(id);
        if (!type.equals(l.getClass())) {
            return null;
        }
        return (T)l;
    }
    
    private class DirectionalContainer extends GameEntityContainer<DirectionalLight> {
        
        private final Transform transform = new Transform();
        
        public DirectionalContainer(EntityData ed) {
            super(ed, filter(EntityLight.DIRECTIONAL), EntityLight.class);
        }
        
        @Override
        protected DirectionalLight addObject(Entity entity) {
            var light = new DirectionalLight();
            lazyObjectUpdate(light, entity);
            persistentObjectUpdate(light, entity);
            rootNode.addLight(light);
            return light;
        }
        @Override
        protected void lazyObjectUpdate(DirectionalLight t, Entity entity) {
            t.setColor(entity.get(EntityLight.class).getColor());
        }
        @Override
        protected void persistentObjectUpdate(DirectionalLight t, Entity entity) {
            t.setDirection(GameUtils.getWorldTransform(ed, entity.getId(), transform).getRotation().mult(Vector3f.UNIT_Z));            
        }
        @Override
        protected void removeObject(DirectionalLight t, Entity entity) {
            rootNode.removeLight(t);
        }
        
    }
    private class PointContainer extends GameEntityContainer<PointLight> {
        
        private final Transform transform = new Transform();
        
        public PointContainer(EntityData ed) {
            super(ed, filter(EntityLight.POINT), EntityLight.class, Power.class);
        }
        
        @Override
        protected PointLight addObject(Entity entity) {
            var light = new PointLight();
            lazyObjectUpdate(light, entity);
            persistentObjectUpdate(light, entity);
            rootNode.addLight(light);
            return light;
        }
        @Override
        protected void lazyObjectUpdate(PointLight t, Entity entity) {
            t.setRadius(entity.get(Power.class).getPower());
            t.setColor(entity.get(EntityLight.class).getColor());
        }
        @Override
        protected void persistentObjectUpdate(PointLight t, Entity entity) {
            t.setPosition(GameUtils.getWorldTransform(ed, entity.getId(), transform).getTranslation());
        }
        @Override
        protected void removeObject(PointLight t, Entity entity) {
            rootNode.removeLight(t);
        }
        
    }
    private class SpotContainer extends GameEntityContainer<SpotLight> {
        
        private final Transform transform = new Transform();

        public SpotContainer(EntityData ed) {
            super(ed, filter(EntityLight.SPOT), EntityLight.class, Power.class, InfluenceCone.class);
        }

        @Override
        protected SpotLight addObject(Entity entity) {
            var light = new SpotLight();
            lazyObjectUpdate(light, entity);
            persistentObjectUpdate(light, entity);
            rootNode.addLight(light);
            return light;
        }
        @Override
        protected void lazyObjectUpdate(SpotLight t, Entity entity) {
            t.setSpotRange(entity.get(Power.class).getPower());
            entity.get(InfluenceCone.class).applyToSpotLight(t);
            t.setColor(entity.get(EntityLight.class).getColor());
        }
        @Override
        protected void persistentObjectUpdate(SpotLight t, Entity entity) {
            GameUtils.getWorldTransform(ed, entity.getId(), transform);
            t.setPosition(transform.getTranslation());
            t.setDirection(transform.getRotation().mult(Vector3f.UNIT_Z));
        }
        @Override
        protected void removeObject(SpotLight t, Entity entity) {
            rootNode.removeLight(t);
        }
        
    }
    private class AmbientContainer extends GameEntityContainer<AmbientLight> {

        public AmbientContainer(EntityData ed) {
            super(ed, filter(EntityLight.AMBIENT), EntityLight.class);
        }

        @Override
        protected AmbientLight addObject(Entity entity) {
            var light = new AmbientLight();
            lazyObjectUpdate(light, entity);
            rootNode.addLight(light);
            return light;
        }
        @Override
        protected void lazyObjectUpdate(AmbientLight t, Entity entity) {
            t.setColor(entity.get(EntityLight.class).getColor());
        }
        @Override
        protected void persistentObjectUpdate(AmbientLight t, Entity entity) {}
        @Override
        protected void removeObject(AmbientLight t, Entity entity) {
            rootNode.removeLight(t);
        }
        
    }
    private class ProbeContainer extends GameEntityContainer<LightProbe> {
        
        private final Transform transform = new Transform();

        public ProbeContainer(EntityData ed) {
            super(ed, filter(EntityLight.PROBE), EntityLight.class, Position.class);
        }
        
        @Override
        protected LightProbe addObject(Entity e) {
            var p = VfxUtils.loadLightProbe(assetManager, e.get(LightProbeSource.class).getPath());
            if (p == null) {
                throw new NullPointerException("Failed to locate light probe from "+e.get(LightProbeSource.class));
            }
            rootNode.addLight(p);
            return p;
        }
        @Override
        protected void lazyObjectUpdate(LightProbe object, Entity e) {}
        @Override
        protected void persistentObjectUpdate(LightProbe object, Entity e) {
            GameUtils.getWorldTransform(ed, e.getId(), transform);
            object.setPosition(transform.getTranslation());
        }
        @Override
        protected void removeObject(LightProbe object, Entity e) {
            rootNode.removeLight(object);
        }
        
    }
    
    private static FunctionFilter<EntityLight> filter(int lightType) {
        return new FunctionFilter<>(EntityLight.class, c -> c.getType() == lightType);
    }
    
}