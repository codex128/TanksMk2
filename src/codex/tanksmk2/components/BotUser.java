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
public class BotUser implements EntityComponent {
    
    public static final int MAIN = 0, MOVE = 1, AIM = 2, SHOOT = 3;
    public static final int EXTENT = 4;
    
    private final int[] elements;
    
    public BotUser(int... elements) {
        this.elements = elements;
    }

    public int[] getElements() {
        return elements;
    }
    public boolean isMain() {
        for (int j : elements) {
            if (j == MAIN) return true;
        }
        return false;
    }
    
}
