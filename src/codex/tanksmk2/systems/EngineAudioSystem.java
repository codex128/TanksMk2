/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.systems;

import codex.tanksmk2.components.AudioInput;
import codex.tanksmk2.components.EngineAudio;
import codex.tanksmk2.components.EngineStatus;
import codex.tanksmk2.components.TankMoveDirection;
import codex.tanksmk2.components.TargetTo;
import codex.tanksmk2.util.GameUtils;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.sim.AbstractGameSystem;
import com.simsilica.sim.SimTime;

/**
 *
 * @author codex
 */
public class EngineAudioSystem extends AbstractGameSystem {

    private EntityData ed;
    private EntitySet entities;
    
    @Override
    protected void initialize() {
        ed = getManager().get(EntityData.class);
        entities = ed.getEntities(EngineAudio.class, EngineStatus.class);
    }
    @Override
    protected void terminate() {
        entities.release();
    }
    @Override
    public void update(SimTime time) {
        if (entities.applyChanges()) {
            entities.getAddedEntities().forEach(e -> updateEngineSound(e, time));
            entities.getChangedEntities().forEach(e -> updateEngineSound(e, time));
        }
    }
    
    private void updateEngineSound(Entity e, SimTime time) {
        var s = e.get(EngineStatus.class);
        switch (s.getStatus()) {
            case EngineStatus.WORKING -> {
                set(e, time, EngineStatus.WORKING, AudioInput.PLAY_LOOPED);
                set(e, time, EngineStatus.IDLE, AudioInput.STOP);
            }
            case EngineStatus.IDLE -> {
                set(e, time, EngineStatus.WORKING, AudioInput.STOP);
                set(e, time, EngineStatus.IDLE, AudioInput.PLAY_LOOPED);
            }
            case EngineStatus.BOOSTING -> {
                set(e, time, EngineStatus.BOOSTING, AudioInput.PLAY_INSTANCE);
                set(e, time, EngineStatus.WORKING, AudioInput.PLAY_LOOPED);
                set(e, time, EngineStatus.IDLE, AudioInput.STOP);
            }
        }
    }    
    private void set(Entity e, SimTime time, int channel, int input) {
        ed.setComponents(ed.createEntity(),
            new TargetTo(e.get(EngineAudio.class).get(channel)),
            new AudioInput(input),
            GameUtils.duration(time, .2)
        );
    }
    
}
