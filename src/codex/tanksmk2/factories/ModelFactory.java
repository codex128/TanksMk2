/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class ModelFactory implements Factory<Spatial> {

    private final EntityData ed;
    private final AssetManager assetManager;

    public ModelFactory(EntityData ed, AssetManager assetManager) {
        this.ed = ed;
        this.assetManager = assetManager;
    }
    
    @Override
    public Spatial load(EntityId customer, String name) {
        return switch (name) {
            case "tank" -> createTank();
            default -> null;
        };
    }
    
    private Spatial createTank() {
        var tank = assetManager.loadModel("Models/tank/tank.j3o");
        var mat = new Material(assetManager, "MatDefs/tank.j3md");
        mat.setTexture("DiffuseMap", assetManager.loadTexture(new TextureKey("Textures/tankDiffuse.png", false)));
        tank.setMaterial(mat);
        return tank;
    }
    
}
