/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;

/**
 *
 * @author codex
 */
public interface ModelProcessor {
    
    public static final ModelProcessor CullHint = (Spatial spatial) -> {
        String hint = spatial.getUserData("CullHint");
        if (hint != null) {
            spatial.setCullHint(Enum.valueOf(Spatial.CullHint.class, hint));
        }
    };
    
    public static final ModelProcessor ShadowMode = (Spatial spatial) -> {
        String mode = spatial.getUserData("ShadowMode");
        if (mode != null) {
            spatial.setShadowMode(Enum.valueOf(RenderQueue.ShadowMode.class, mode));
        }
    };
    
    public void processSpatial(Spatial spatial);
    
    public default void processSceneRoot(Spatial spatial) {}
    
}
