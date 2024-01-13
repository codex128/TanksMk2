/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.jme3.audio.AudioData.DataType;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class AudioLoadType implements EntityComponent {
    
    private final DataType type;

    public AudioLoadType(DataType type) {
        this.type = type;
    }

    public DataType getType() {
        return type;
    }
    @Override
    public String toString() {
        return "AudioLoadType{" + type + '}';
    }
    
}
