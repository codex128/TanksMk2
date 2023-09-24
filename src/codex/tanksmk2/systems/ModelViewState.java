/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.ESAppState;
import codex.tanksmk2.components.Bone;
import codex.tanksmk2.components.EntityTransform;
import codex.tanksmk2.components.ModelInfo;
import codex.tanksmk2.factories.ModelFactory;
import com.jme3.anim.SkinningControl;
import com.jme3.app.Application;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityContainer;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;

/**
 *
 * @author codex
 */
public class ModelViewState extends ESAppState {

    private ModelContainer models;
    private EntitySet bones;
    private ModelFactory factory;
    
    @Override
    protected void init(Application app) {
        models = new ModelContainer(ed);
        bones = ed.getEntities(Bone.class, EntityTransform.class);
        if (factory == null) {
            // todo: set default factory
        }
    }
    @Override
    protected void cleanup(Application app) {}
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
        if (bones.applyChanges()) {
            bones.getAddedEntities().forEach(e -> updateBoneTransform(e));
            bones.getChangedEntities().forEach(e -> updateBoneTransform(e));
        }
    }
    
    private void updateBoneTransform(Entity e) {
        var view = models.getObject(e.get(Bone.class).getModel());
        if (view == null) return;
        var skin = view.spatial.getControl(SkinningControl.class);
        if (skin == null) return;
        var j = skin.getArmature().getJoint(e.get(Bone.class).getBone());
        if (j == null) return;
        j.setLocalRotation(e.get(EntityTransform.class).getRotation());
    }
    
    protected Spatial createModel(ModelInfo info) {
        return factory.apply(info);
    }
    protected void attachModel(Spatial model) {
        rootNode.attachChild(model);
    }
    
    private class ModelView {
        
        private final Entity entity;
        private final Spatial spatial;
        
        public ModelView(Entity entity) {
            this.entity = entity;
            spatial = createModel(entity.get(ModelInfo.class));
            attachModel(spatial);
            update();
        }
        
        public final void update() {
            var transform = entity.get(EntityTransform.class);
            spatial.setLocalTranslation(transform.getTranslation());
            spatial.setLocalRotation(transform.getRotation());
        }
        public void destroy() {
            spatial.removeFromParent();
        }
        
    }    
    private class ModelContainer extends EntityContainer<ModelView> {

        public ModelContainer(EntityData ed) {
            super(ed, ModelInfo.class, EntityTransform.class);
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
