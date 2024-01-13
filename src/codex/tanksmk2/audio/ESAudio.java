/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.audio;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioContext;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioKey;
import com.jme3.audio.AudioParam;
import com.jme3.audio.AudioRenderer;
import com.jme3.audio.AudioSource;
import com.jme3.audio.Filter;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author codex
 */
public class ESAudio extends Node implements AudioSource {
    
    private AudioData data;
    private AudioModel model;
    private int channel = -1;
    private Filter dryFilter;
    private Filter reverbFilter;
    private Status status = Status.Stopped;
    private Vector3f velocity = new Vector3f();
    private float[] angles = {360, 360};
    private float volumeFactor = 1;
    
    public ESAudio(AssetManager assetManager, String path) {
        data = assetManager.loadAudio(path);
    }
    public ESAudio(AssetManager assetManager, AudioKey key) {
        data = assetManager.loadAudio(key);
    }
    
    @Override
    public void updateLogicalState(float tpf) {
        super.updateLogicalState(tpf);
    }
    
    public void playInstance() {
        if (isPositional() && !supportsPositional()) {
            throw new IllegalStateException("Positional audio only supports one channel.");
        }
        getRenderer().playSourceInstance(this);
    }
    public void play() {
        if (isPositional() && !supportsPositional()) {
            throw new IllegalStateException("Positional audio only supports one channel.");
        }
        getRenderer().playSource(this);
    }
    public void pause() {
        getRenderer().pauseSource(this);
    }
    public void stop() {
        getRenderer().stopSource(this);
    }
    
    protected AudioRenderer getRenderer() {
        var renderer = AudioContext.getAudioRenderer();
        if (renderer == null) {
            throw new NullPointerException("No audio renderer available.");
        }
        return renderer;
    }    
    public void updateSourceParam(AudioParam param) {
        if (isPlaying()) {
            getRenderer().updateSourceParam(this, param);
        }
    }
    
    public void setDryFilter(Filter dryFilter) {
        this.dryFilter = dryFilter;
        updateSourceParam(AudioParam.DryFilter);
    }
    public void setReverbFilter(Filter reverbFilter) {
        this.reverbFilter = reverbFilter;
        updateSourceParam(AudioParam.ReverbFilter);
    }
    public void setVelocity(Vector3f velocity) {
        this.velocity.set(velocity);
        updateSourceParam(AudioParam.Velocity);
    }
    public void setInnerAngle(float inner) {
        angles[0] = inner;
        updateSourceParam(AudioParam.InnerAngle);
    }
    public void setOuterAngle(float outer) {
        angles[1] = outer;
        updateSourceParam(AudioParam.OuterAngle);
    }
    public void setAngles(float inner, float outer) {
        setInnerAngle(inner);
        setOuterAngle(outer);
    }
    public void setVolumeFactor(float factor) {
        this.volumeFactor = factor;
        updateSourceParam(AudioParam.Volume);
    }
    
    public void setVolume(float volume) {
        model.setVolume(volume);
        updateSourceParam(AudioParam.Volume);
    }
    public void setPitch(float pitch) {
        model.setPitch(pitch);
        updateSourceParam(AudioParam.Pitch);
    }
    public void setLooping(boolean looping) {
        model.setLooping(looping);
        updateSourceParam(AudioParam.Looping);
    }
    public void setRefDistance(float refDist) {
        model.setRefDistance(refDist);
        updateSourceParam(AudioParam.RefDistance);
    }
    public void setMaxDistance(float maxDist) {
        model.setMaxDistance(maxDist);
        updateSourceParam(AudioParam.MaxDistance);
    }

    public AudioData getData() {
        return data;
    }
    public AudioModel getModel() {
        return model;
    }
    public float getVolumeFactor() {
        return volumeFactor;
    }
    public boolean isPlaying() {
        return channel >= 0;
    }
    public boolean supportsPositional() {
        return data.getChannels() == 1;
    }
    
    @Override
    public void setChannel(int channel) {
        this.channel = channel;
    }
    @Override
    public int getChannel() {
        return channel;
    }
    @Override
    public Filter getDryFilter() {
        return dryFilter;
    }
    @Override
    public AudioData getAudioData() {
        return data;
    }
    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
    @Override
    public Status getStatus() {
        return status;
    }
    @Override
    public boolean isLooping() {
        return model.isLooping();
    }
    @Override
    public float getPitch() {
        return model.getPitch();
    }
    @Override
    public float getVolume() {
        return model.getVolume()*volumeFactor;
    }
    @Override
    public float getTimeOffset() {
        return model.getTimeOffset();
    }
    @Override
    public float getPlaybackTime() {
        if (channel >= 0) {
            return getRenderer().getSourcePlaybackTime(this);
        } else {
            return 0;
        }
    }
    @Override
    public Vector3f getPosition() {
        return getWorldTranslation();
    }
    @Override
    public Vector3f getVelocity() {
        return velocity;
    }
    @Override
    public boolean isReverbEnabled() {
        return model.isReverb();
    }
    @Override
    public Filter getReverbFilter() {
        return reverbFilter;
    }
    @Override
    public float getMaxDistance() {
        return model.getMaxDistance();
    }
    @Override
    public float getRefDistance() {
        return model.getRefDistance();
    }
    @Override
    public boolean isDirectional() {
        return model.isDirectional();
    }
    @Override
    public Vector3f getDirection() {
        return getWorldRotation().getRotationColumn(2);
    }
    @Override
    public float getInnerAngle() {
        return angles[0];
    }
    @Override
    public float getOuterAngle() {
        return angles[1];
    }
    @Override
    public boolean isPositional() {
        return model.isPositional();
    }
    
}
