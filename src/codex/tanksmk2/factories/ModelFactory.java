/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import codex.tanksmk2.components.ModelInfo;
import com.jme3.scene.Spatial;

/**
 *
 * @author codex
 */
public interface ModelFactory {
    
    public Spatial apply(ModelInfo info);
    public Spatial createModel(String name);
    
}
