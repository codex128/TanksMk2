/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import com.jme3.audio.Filter;
import com.jme3.math.FastMath;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class AudioFactory implements Factory<AudioNode> {

    private final AssetManager assetManager;

    public AudioFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    
    @Override
    public AudioNode load(FactoryInfo info, EntityId customer) {
        return switch (info.name) {
            case "gunShot" -> createGunShot(info);
            default -> throw new UnsupportedOperationException("Unsupported audio manufacturing process!");
        };
    }
    
    public AudioNode createGunShot(FactoryInfo info) {
        var audio = new AudioNode(assetManager, "Sounds/guns/bang"+info.variation+".wav", DataType.Buffer);
        audio.setVolume(1f);
        audio.setPositional(true);
        audio.setDirectional(true);
        audio.setInnerAngle(FastMath.QUARTER_PI);
        audio.setOuterAngle(FastMath.HALF_PI);
        return audio;
    }
    
}
