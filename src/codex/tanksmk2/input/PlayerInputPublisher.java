/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.input;

/**
 * Publishes hardware input as components.
 * 
 * @author codex
 */
public interface PlayerInputPublisher {
    
    public void onEnable();
    public void onDisable();
    public void update(float tpf);
    public Functions getFunctions();
    
}
