/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.states;

import codex.tanksmk2.ESAppState;
import com.jme3.app.Application;
import com.jme3.audio.Listener;
import com.simsilica.es.EntitySet;

/**
 *
 * @author codex
 */
public class AudioState extends ESAppState {
    
    private Listener listener;
    private EntitySet entities;

    @Override
    protected void init(Application app) {
        listener = app.getListener();
    }
    @Override
    protected void cleanup(Application app) {}
    @Override
    protected void onEnable() {}
    @Override
    protected void onDisable() {}    
    @Override
    public void update(float tpf) {}
    
}
