/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class LightProbeSource implements EntityComponent {
    
    private final String path;

    public LightProbeSource(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "LightProbeSource{" + "path=" + path + '}';
    }
    
}
