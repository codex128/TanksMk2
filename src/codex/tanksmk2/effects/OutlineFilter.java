/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.effects;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.post.Filter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.texture.Image;

/**
 *
 * @author codex
 */
public class OutlineFilter extends Filter {
    
    private AssetManager assetManager;
    private RenderManager renderManager;
    private ViewPort viewPort;
    private Pass extract;
    
    @Override
    protected void initFilter(AssetManager assetManager, RenderManager renderManager, ViewPort vp, int w, int h) {
        this.assetManager = assetManager;
        this.renderManager = renderManager;
        this.viewPort = vp;
        extract = new Pass();
        extract.init(renderManager.getRenderer(), w, h, Image.Format.RGBA8, Image.Format.Depth);
        material = new Material(assetManager, "MatDefs/filters/outline.j3md");
        material.setTexture("OutlineMap", extract.getRenderedTexture());
        material.setVector2("Resolution", new Vector2f(w, h));
    }
    @Override
    protected Material getMaterial() {
        return material;
    }
    @Override
    protected void postQueue(RenderQueue queue) {
        var r = renderManager.getRenderer();
        r.setBackgroundColor(ColorRGBA.BlackNoAlpha);
        r.setFrameBuffer(extract.getRenderFrameBuffer());
        r.clearBuffers(true, true, true);
        renderManager.setForcedTechnique("Outline");
        renderManager.renderViewPortQueues(viewPort, false);
        renderManager.setForcedTechnique(null);
        r.setFrameBuffer(viewPort.getOutputFrameBuffer());
    }
    
}
