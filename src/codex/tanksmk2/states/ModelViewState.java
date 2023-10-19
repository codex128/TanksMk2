/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.states;

import codex.boost.scene.SceneGraphIterator;
import codex.tanksmk2.ESAppState;
import codex.tanksmk2.bullet.GeometricShape;
import codex.tanksmk2.components.ApplyBonePosition;
import codex.tanksmk2.components.ApplyBoneRotation;
import codex.tanksmk2.components.BoneInfo;
import codex.tanksmk2.components.EmitOnce;
import codex.tanksmk2.components.MatValue;
import codex.tanksmk2.components.ModelInfo;
import codex.tanksmk2.components.GeometricShapeInfo;
import codex.tanksmk2.components.Parent;
import codex.tanksmk2.components.Position;
import codex.tanksmk2.components.Rotation;
import codex.tanksmk2.components.Scene;
import codex.tanksmk2.components.TargetTo;
import codex.tanksmk2.util.GameUtils;
import com.jme3.anim.SkinningControl;
import com.jme3.app.Application;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;
import codex.tanksmk2.factories.Factory;
import codex.tanksmk2.factories.FactoryInfo;
import codex.tanksmk2.factories.ModelFactory;
import codex.tanksmk2.factories.Prefab;
import codex.tanksmk2.factories.SceneEntityFactory;
import codex.tanksmk2.util.GameEntityContainer;
import com.epagagames.particles.Emitter;
import com.jme3.anim.Joint;
import com.jme3.scene.Geometry;
import com.simsilica.bullet.CollisionShapes;
import com.simsilica.bullet.ShapeInfo;
import com.simsilica.es.EntityId;
import com.simsilica.state.GameSystemsState;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author codex
 */
public class ModelViewState extends ESAppState {
    
    /**
     * Userdata key for marking a model as an entity in a scene.
     */
    public static final String ENTITY = "Entity";
    
    /**
     * {@link Prefab} name for entities that want to retrieve a model from the cache.
     */
    public static final String CACHE = "special:cached_model";
    
    private ModelContainer models;
    private EntitySet bonePosition, boneRotation, materials, geometryShapes, emitOnce;
    private Factory<Spatial> modelFactory;
    private FactoryInfo factoryInfo = new FactoryInfo();
    private CollisionShapes shapes;
    private final HashMap<EntityId, Spatial> modelCache = new HashMap<>();
    
    public ModelViewState() {}
    public ModelViewState(Factory<Spatial> modelFactory) {
        this.modelFactory = modelFactory;
    }
    
    @Override
    protected void init(Application app) {
        System.out.println("init model view state");
        models = new ModelContainer();
        System.out.println(1);
        bonePosition = ed.getEntities(BoneInfo.class, ApplyBonePosition.class, Position.class);
        boneRotation = ed.getEntities(BoneInfo.class, ApplyBoneRotation.class, Rotation.class);
        materials = ed.getEntities(MatValue.class, TargetTo.class);
        geometryShapes = ed.getEntities(ModelInfo.class, GeometricShapeInfo.class);
        emitOnce = ed.getEntities(ModelInfo.class, EmitOnce.class);
        if (modelFactory == null) {
            modelFactory = new ModelFactory(ed, assetManager);
        }
        factoryInfo.setEntityData(ed);
        shapes = getState(GameSystemsState.class).get(CollisionShapes.class);
        if (shapes == null) {
            throw new NullPointerException("Could not locate collision shapes!");
        }
    }
    @Override
    protected void cleanup(Application app) {
        bonePosition.release();
        boneRotation.release();
        materials.release();
        geometryShapes.release();
        modelCache.clear();
        emitOnce.clear();
    }
    @Override
    protected void onEnable() {
        models.start();
    }
    @Override
    protected void onDisable() {
        models.stop();
    }
    @Override
    public void update(float tpf) {
        boolean gsu = geometryShapes.applyChanges();
        models.update();
        if (bonePosition.applyChanges()) {
            bonePosition.getAddedEntities().forEach(e -> updateBonePositionFromEntity(e));
            bonePosition.getChangedEntities().forEach(e -> updateBonePositionFromEntity(e));
        }
        bonePosition.forEach(e -> updateEntityPositionFromBone(e));
        if (boneRotation.applyChanges()) {
            boneRotation.getAddedEntities().forEach(e -> updateBoneRotationFromEntity(e));
            boneRotation.getChangedEntities().forEach(e -> updateBoneRotationFromEntity(e));
        }
        boneRotation.forEach(e -> updateEntityRotationFromBone(e));
        if (materials.applyChanges()) {
            materials.getAddedEntities().forEach(e -> updateMaterial(e));
            materials.getChangedEntities().forEach(e -> updateMaterial(e));
        }
        if (gsu) {
            geometryShapes.getAddedEntities().forEach(e -> createGeometryShape(e));
        }
        if (emitOnce.applyChanges()) for (var e : emitOnce) {
            var view = models.getObject(e.getId());
            if (view != null && view.spatial instanceof Emitter) {
                ((Emitter)view.spatial).emitAllParticles();
            }
            // consume the activator component
            ed.removeComponent(e.getId(), EmitOnce.class);
        }
    }
    
    private void updateBonePositionFromEntity(Entity e) {        
        if (e.get(ApplyBonePosition.class).isDirection() != ApplyBonePosition.ENTITY_TO_BONE) {
            return;
        }
        var j = getBone(e);
        if (j == null) return;
        j.setLocalTranslation(e.get(Position.class).getPosition());
    }
    private void updateEntityPositionFromBone(Entity e) {
        if (e.get(ApplyBonePosition.class).isDirection() != ApplyBonePosition.BONE_TO_ENTITY) {
            return;
        }
        var j = getBone(e);
        if (j == null) return;
        e.set(new Position(j.getLocalTranslation()));
    }
    private void updateBoneRotationFromEntity(Entity e) {
        if (e.get(ApplyBoneRotation.class).isDirection() != ApplyBoneRotation.ENTITY_TO_BONE) {
            return;
        }
        var j = getBone(e);
        if (j == null) return;
        j.setLocalRotation(e.get(Rotation.class).getRotation());
    }
    private void updateEntityRotationFromBone(Entity e) {
        if (e.get(ApplyBoneRotation.class).isDirection() != ApplyBoneRotation.BONE_TO_ENTITY) {
            return;
        }
        var j = getBone(e);
        if (j == null) return;
        e.set(new Rotation(j.getLocalRotation()));
    }
    private Joint getBone(Entity e) {
        var view = models.getObject(e.get(BoneInfo.class).getModel());
        if (view == null) return null;
        var skin = view.spatial.getControl(SkinningControl.class);
        if (skin == null) return null;
        return skin.getArmature().getJoint(e.get(BoneInfo.class).getBone());
    }
    
    private void updateMaterial(Entity e) {
        var view = models.getObject(e.get(TargetTo.class).getTargetId());
        if (view != null) {
            var value = e.get(MatValue.class);
            for (var spatial : new SceneGraphIterator(view.spatial)) {
                if (spatial instanceof Geometry) {
                    ((Geometry)spatial).getMaterial().setParam(value.getName(), value.getType(), value.getValue());
                }
            }
        }
    }
    private void createGeometryShape(Entity e) {
        var spatial = getSpatial(e.getId());
        if (spatial == null) return;
        var g = e.get(GeometricShapeInfo.class);
        var info = new ShapeInfo(g.getPrefab().getId());
        if (shapes.getShape(info) == null) {
            shapes.register(info, GameUtils.createGeometricCollisionShape(Enum.valueOf(GeometricShape.class, g.getType()), spatial));
            if (ed.getComponent(e.getId(), ShapeInfo.class) == null) {
                ed.setComponent(e.getId(), info);
            }
        }
        ed.removeComponent(e.getId(), GeometricShapeInfo.class);
    }
    private void prepareScene(Spatial scene) {
        var list = new LinkedList<Spatial>();
        for (var spatial : new SceneGraphIterator(scene)) {
            String name = spatial.getUserData(ENTITY);
            if (name != null) {
                var id = SceneEntityFactory.create(new FactoryInfo(name, ed, app), spatial);
                if (id != null) {
                    var parent = GameUtils.fetchId(spatial, -1);
                    if (parent != null) {
                        ed.setComponent(id, new Parent(parent));
                    }
                    // check if the entity actually wants to control that spatial
                    var model = ed.getComponent(id, ModelInfo.class);
                    if (model != null && model.getName(ed).equals(CACHE)) {
                        GameUtils.appendId(id, spatial);
                        cacheModel(id, spatial);
                        list.add(spatial);
                    }
                }
            }
        }
        for (var spatial : list) {
            // This has to be done afterward, in order to not
            // interrupt the hierarchy during iteration.
            rootNode.attachChild(spatial);
        }
        list.clear();
    }
    
    private Spatial createModel(EntityId customer, ModelInfo info) {
        // check if a model is already cached for this entity
        var spatial = modelCache.remove(customer);
        if (spatial == null) {
            // manufacture a new model for the entity
            factoryInfo.setPrefab(info.getPrefab());
            spatial = modelFactory.load(factoryInfo, customer);
            if (spatial == null) {
                throw new NullPointerException("Prefab \""+info.getPrefab().getName(ed)+"\" failed to manufacture model!");
            }
        }
        // append the id to the spatial
        GameUtils.appendId(customer, spatial);
        return spatial;
    }
    private void cacheModel(EntityId id, Spatial spatial) {
        modelCache.put(id, spatial);
    }
    public Spatial getSpatial(EntityId id) {
        var view = models.getObject(id);
        if (view == null) return null;
        return view.spatial;
    }
    
    private class ModelView {
        
        private final Entity entity;
        private final Spatial spatial;
        
        public ModelView(Entity entity) {
            this.entity = entity;
            spatial = createModel(entity.getId(), entity.get(ModelInfo.class));
            lazyUpdate();
            persistentUpdate();
            var s = ed.getComponent(entity.getId(), Scene.class);
            if (s != null) {
                prepareScene(spatial);
            }
        }
        
        public final void lazyUpdate() {
            boolean visible = entity.get(ModelInfo.class).isVisible();
            if (visible && spatial.getParent() == null) {
                rootNode.attachChild(spatial);
            }
            else if (!visible && spatial.getParent() != null) {
                spatial.removeFromParent();
            }
        }
        public final void persistentUpdate() {            
            var transform = GameUtils.getWorldTransform(ed, entity.getId());
            spatial.setLocalTranslation(transform.getTranslation());
            spatial.setLocalRotation(transform.getRotation());
        }
        public void destroy() {
            spatial.removeFromParent();
        }
        
    }
    private class ModelContainer extends GameEntityContainer<ModelView> {

        public ModelContainer() {
            super(ed, ModelInfo.class);
        }
        
        @Override
        protected ModelView addObject(Entity entity) {
            return new ModelView(entity);
        }
        @Override
        protected void lazyObjectUpdate(ModelView t, Entity entity) {
            t.lazyUpdate();
        }
        @Override
        public void persistentObjectUpdate(ModelView t, Entity entity) {
            t.persistentUpdate();
        }
        @Override
        protected void removeObject(ModelView t, Entity entity) {
            t.destroy();
        }
        
    }
    
}
