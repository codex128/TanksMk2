/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

/**
 *
 * @author codex
 */
public class Tread implements EntityComponent {
    
    private final String param;
    private final float position;

    public Tread(String param, float position) {
        this.param = param;
        this.position = position;
    }

    public String getParam() {
        return param;
    }
    public float getPosition() {
        return position;
    }
    public Tread add(float position) {
        return new Tread(param, this.position+position);
    }
        
}
