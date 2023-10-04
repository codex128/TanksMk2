/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.ESAppState;
import codex.tanksmk2.components.BoneInfo;
import codex.tanksmk2.components.ModelInfo;
import codex.tanksmk2.components.Position;
import codex.tanksmk2.components.Rotation;
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
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class ModelViewState extends ESAppState {

    private ModelContainer models;
    private EntitySet bones;
    private Factory<Spatial> factory;
    
    @Override
    protected void init(Application app) {
        System.out.println("initialize model view state");
        models = new ModelContainer(ed);
        //models.start();
        bones = ed.getEntities(BoneInfo.class, Rotation.class);
        if (factory == null) {
            factory = new ModelFactory(ed, assetManager);
        }
    }
    @Override
    protected void cleanup(Application app) {
        //models.stop();
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
        if (bones.applyChanges()) {
            bones.getAddedEntities().forEach(e -> updateBoneTransform(e));
            bones.getChangedEntities().forEach(e -> updateBoneTransform(e));
        }
    }
    
    private void updateBoneTransform(Entity e) {
        var view = models.getObject(e.get(BoneInfo.class).getModel());
        if (view == null) return;
        var skin = view.spatial.getControl(SkinningControl.class);
        if (skin == null) return;
        var j = skin.getArmature().getJoint(e.get(BoneInfo.class).getBone());
        if (j == null) return;
        // only rotation is being applied for now
        j.setLocalRotation(e.get(Rotation.class).getRotation());
        ed.setComponent(e.getId(), new Position(j.getLocalTranslation()));
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
            System.out.println("model attached");
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
            System.out.println("add model");
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
