/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.audio;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioKey;

/**
 *
 * @author codex
 */
public class AudioModel {
    
    private String asset;
    private float volume = 1;
    private float pitch = 1;
    private boolean looping = false;
    private boolean positional = false;
    private boolean directional = false;
    private boolean reverb = true;
    private float timeOffset = 0;
    private float refDist = 10;
    private float maxDist = 20;
    private DataType dataType = DataType.Buffer;

    public AudioData loadData(AssetManager assetManager) {
        var key = new AudioKey();
        var data = assetManager.loadAudio(key);
        return data;
    }
    
    public void setAssetPath(String path) {
        this.asset = path;
    }
    public void setVolume(float volume) {
        this.volume = volume;
    }
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
    public void setLooping(boolean looping) {
        this.looping = looping;
    }
    public void setPositional(boolean positional) {
        this.positional = positional;
    }
    public void setDirectional(boolean directional) {
        this.directional = directional;
    }
    public void setReverb(boolean reverb) {
        this.reverb = reverb;
    }
    public void setTimeOffset(float timeOffset) {
        this.timeOffset = timeOffset;
    }
    public void setRefDistance(float refDist) {
        this.refDist = refDist;
    }
    public void setMaxDistance(float maxDist) {
        this.maxDist = maxDist;
    }
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
    
    public String getAssetPath() {
        return asset;
    }
    public float getVolume() {
        return volume;
    }
    public float getPitch() {
        return pitch;
    }
    public boolean isLooping() {
        return looping;
    }
    public boolean isPositional() {
        return positional;
    }
    public boolean isDirectional() {
        return directional;
    }
    public boolean isReverb() {
        return reverb;
    }
    public float getTimeOffset() {
        return timeOffset;
    }
    public float getRefDistance() {
        return refDist;
    }
    public float getMaxDistance() {
        return maxDist;
    }
    public DataType getDataType() {
        return dataType;
    }
    
}
