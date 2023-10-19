/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.states;

import codex.tanksmk2.ESAppState;
import codex.tanksmk2.components.EntityLight;
import codex.tanksmk2.components.LightCone;
import codex.tanksmk2.components.LightColor;
import codex.tanksmk2.components.Position;
import codex.tanksmk2.components.Power;
import codex.tanksmk2.components.Rotation;
import codex.tanksmk2.util.FunctionFilter;
import codex.tanksmk2.util.GameEntityContainer;
import codex.tanksmk2.util.GameUtils;
import com.jme3.app.Application;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.light.PointLight;
import com.jme3.light.SpotLight;
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
        entities.forEach(e -> e.update());
    }
    
    public Light getLight(EntityId id) {
        for (var container : entities) {
            var l = container.getObject(id);
            if (l != null) return l;
        }
        return null;
    }
    
    private class DirectionalContainer extends GameEntityContainer<DirectionalLight> {
        
        public DirectionalContainer(EntityData ed) {
            super(ed, filter(EntityLight.DIRECTIONAL), EntityLight.class, Rotation.class, LightColor.class);
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
            t.setColor(entity.get(LightColor.class).getColor());
        }
        @Override
        protected void persistentObjectUpdate(DirectionalLight t, Entity entity) {
            t.setDirection(GameUtils.getWorldTransform(ed, entity.getId()).getRotation().mult(Vector3f.UNIT_Z));            
        }
        @Override
        protected void removeObject(DirectionalLight t, Entity entity) {
            rootNode.removeLight(t);
        }
        
    }
    private class PointContainer extends GameEntityContainer<PointLight> {
        
        public PointContainer(EntityData ed) {
            super(ed, filter(EntityLight.POINT), EntityLight.class, Position.class, Power.class, LightColor.class);
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
            t.setColor(entity.get(LightColor.class).getColor());
        }
        @Override
        protected void persistentObjectUpdate(PointLight t, Entity entity) {
            t.setPosition(GameUtils.getWorldTransform(ed, entity.getId()).getTranslation());
        }
        @Override
        protected void removeObject(PointLight t, Entity entity) {
            rootNode.removeLight(t);
        }
        
    }
    private class SpotContainer extends GameEntityContainer<SpotLight> {

        public SpotContainer(EntityData ed) {
            super(ed, filter(EntityLight.SPOT), EntityLight.class, Position.class, Rotation.class, Power.class, LightCone.class, LightColor.class);
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
            entity.get(LightCone.class).applyToSpotLight(t);
            t.setColor(entity.get(LightColor.class).getColor());
        }
        @Override
        protected void persistentObjectUpdate(SpotLight t, Entity entity) {
            var transform = GameUtils.getWorldTransform(ed, entity.getId());
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
            super(ed, filter(EntityLight.AMBIENT), EntityLight.class, LightColor.class);
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
            t.setColor(entity.get(LightColor.class).getColor());
        }
        @Override
        protected void persistentObjectUpdate(AmbientLight t, Entity entity) {}
        @Override
        protected void removeObject(AmbientLight t, Entity entity) {
            rootNode.removeLight(t);
        }
        
    }
    
    private static FunctionFilter<EntityLight> filter(int lightType) {
        return new FunctionFilter<>(EntityLight.class, c -> c.getType() == lightType);
    }
    
}