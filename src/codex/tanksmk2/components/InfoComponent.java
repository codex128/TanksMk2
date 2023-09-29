/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;

/**
 *
 * @author codex
 */
public interface InfoComponent extends EntityComponent {
    
    public int getId();
    public String getName(EntityData ed);
    
}
