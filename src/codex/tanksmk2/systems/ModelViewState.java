/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.boost.scene.SceneGraphIterator;
import codex.tanksmk2.ESAppState;
import codex.tanksmk2.components.ApplyBonePosition;
import codex.tanksmk2.components.ApplyBoneRotation;
import codex.tanksmk2.components.BoneInfo;
import codex.tanksmk2.components.MatValue;
import codex.tanksmk2.components.ModelInfo;
import codex.tanksmk2.components.Position;
import codex.tanksmk2.components.Rotation;
import codex.tanksmk2.components.TargetTo;
import codex.tanksmk2.util.GameUtils;
import com.jme3.anim.SkinningControl;
import com.jme3.app.Application;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityContainer;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import codex.tanksmk2.factories.Factory;
import codex.tanksmk2.factories.ModelFactory;
import com.jme3.anim.Joint;
import com.jme3.scene.Geometry;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class ModelViewState extends ESAppState {

    private ModelContainer models;
    private EntitySet bonePosition, boneRotation, materials;
    private Factory<Spatial> factory;
    
    @Override
    protected void init(Application app) {
        models = new ModelContainer(ed);
        bonePosition = ed.getEntities(BoneInfo.class, ApplyBonePosition.class, Position.class);
        boneRotation = ed.getEntities(BoneInfo.class, ApplyBoneRotation.class, Rotation.class);
        materials = ed.getEntities(MatValue.class, TargetTo.class);
        if (factory == null) {
            factory = new ModelFactory(ed, assetManager);
        }
    }
    @Override
    protected void cleanup(Application app) {
        boneRotation.release();
        materials.release();
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
    }
    
    private void updateBonePositionFromEntity(Entity e) {        
        if (e.get(ApplyBonePosition.class).isDirection() != ApplyBonePosition.BONE_TO_ENTITY) {
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
    
    protected Spatial createModel(EntityId customer, ModelInfo info) {
        return factory.apply(ed, customer, info.getPrefab());
    }
    protected void attachModel(Spatial model) {
        rootNode.attachChild(model);
    }
    
    private class ModelView {
        
        private final Entity entity;
        private final Spatial spatial;
        
        public ModelView(Entity entity) {
            this.entity = entity;
            spatial = createModel(entity.getId(), entity.get(ModelInfo.class));
            attachModel(spatial);
            update();
        }
        
        public final void update() {
            var transform = GameUtils.getWorldTransform(ed, entity);
            spatial.setLocalTranslation(transform.getTranslation());
            spatial.setLocalRotation(transform.getRotation());
        }
        public void destroy() {
            spatial.removeFromParent();
        }
        
    }    
    private class ModelContainer extends EntityContainer<ModelView> {

        public ModelContainer(EntityData ed) {
            super(ed, ModelInfo.class, Position.class, Rotation.class);
        }
        
        @Override
        protected ModelView addObject(Entity entity) {
            return new ModelView(entity);
        }
        @Override
        protected void updateObject(ModelView t, Entity entity) {
            t.update();
        }
        @Override
        protected void removeObject(ModelView t, Entity entity) {
            t.destroy();
        }
        
    }
    
}
