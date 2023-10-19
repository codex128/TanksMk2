/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.states;

import codex.tanksmk2.ESAppState;
import codex.tanksmk2.components.AudioModel;
import codex.tanksmk2.components.AudioInput;
import codex.tanksmk2.components.AudioListener;
import codex.tanksmk2.components.Position;
import codex.tanksmk2.components.Rotation;
import codex.tanksmk2.components.TargetTo;
import codex.tanksmk2.components.Volume;
import codex.tanksmk2.factories.AudioFactory;
import codex.tanksmk2.factories.Factory;
import codex.tanksmk2.factories.FactoryInfo;
import codex.tanksmk2.util.GameEntityContainer;
import codex.tanksmk2.util.GameUtils;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.audio.Listener;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;

/**
 *
 * @author codex
 */
public class AudioState extends ESAppState {
    
    private Listener subject;
    private Entity listener;
    private AudioContainer audio;
    private EntitySet listeners, input;
    private Factory<AudioNode> factory;
    private FactoryInfo factoryInfo = new FactoryInfo();
    
    @Override
    protected void init(Application app) {
        subject = ((SimpleApplication)app).getListener();
        audio = new AudioContainer(ed);
        listeners = ed.getEntities(AudioListener.class, Position.class, Rotation.class);
        input = ed.getEntities(AudioModel.class, AudioInput.class, TargetTo.class);
        if (factory == null) {
            factory = new AudioFactory(assetManager);
        }
        factoryInfo.setEntityData(ed);
        inputManager.getCursorPosition();
    }
    @Override
    protected void cleanup(Application app) {
        input.release();
    }
    @Override
    protected void onEnable() {
        audio.start();
    }
    @Override
    protected void onDisable() {
        audio.stop();
    }    
    @Override
    public void update(float tpf) {
        if (listeners.applyChanges()) {
            if (listeners.getRemovedEntities().contains(listener)) {
                listener = null;
            }
            int currentPriority = listener.get(AudioListener.class).getPriority();
            for (var e : listeners.getAddedEntities()) {
                currentPriority = updateSubjectHolder(e, currentPriority);
            }
            for (var e : listeners.getChangedEntities()) {
                currentPriority = updateSubjectHolder(e, currentPriority);
            }
        }
        if (listener != null) {
            var t = GameUtils.getWorldTransform(ed, listener.getId());
            subject.setLocation(t.getTranslation());
            subject.setRotation(t.getRotation());
        }
        audio.update();
        if (input.applyChanges()) for (var e : input) {
            var a = audio.getObject(e.get(TargetTo.class).getTargetId());
            if (a != null) switch (e.get(AudioInput.class).getInput()) {
                case AudioInput.PLAY          -> a.audio.play();
                case AudioInput.PLAY_INSTANCE -> a.audio.playInstance();
                case AudioInput.PAUSE         -> a.audio.pause();
                case AudioInput.STOP          -> a.audio.stop();
            }
            // consume
            ed.removeComponent(e.getId(), AudioInput.class);
        }
    }
    
    private int updateSubjectHolder(Entity e, int priority) {
        int p = e.get(AudioListener.class).getPriority();
        if (listener == null || p > priority || (p == priority && e.getId().getId() > listener.getId().getId())) {
            listener = e;
            return p;
        }
        return priority;
    }
    private AudioNode createAudio(Entity e) {
        factoryInfo.setPrefab(e.get(AudioModel.class).getPrefab());
        return factory.load(factoryInfo, e.getId());
    }
    
    private class Audio {
        
        private AudioNode audio;
        private Entity entity;
        
        public Audio(Entity entity) {
            audio = createAudio(entity);
            rootNode.attachChild(audio);
            lazyUpdate();
            persistentUpdate();
        }
        
        public final void lazyUpdate() {
            audio.setVolume(entity.get(Volume.class).getVolume());
        }
        public final void persistentUpdate() {
            if (audio.isPositional()) {
                var t = GameUtils.getWorldTransform(ed, entity.getId());
                audio.setLocalTranslation(t.getTranslation());
                if (audio.isDirectional()) {
                    audio.setLocalRotation(t.getRotation());
                }
            }
        }
        public void release() {
            rootNode.detachChild(audio);
        }
        
    }
    private class AudioContainer extends GameEntityContainer<Audio> {

        public AudioContainer(EntityData ed) {
            super(ed, AudioModel.class, Volume.class);
        }
        
        @Override
        protected Audio addObject(Entity entity) {
            return new Audio(entity);
        }
        @Override
        protected void lazyObjectUpdate(Audio t, Entity entity) {
            t.lazyUpdate();
        }
        @Override
        protected void persistentObjectUpdate(Audio t, Entity e) {
            t.persistentUpdate();
        }
        @Override
        protected void removeObject(Audio t, Entity entity) {
            t.release();
        }
        
    }
    
}
