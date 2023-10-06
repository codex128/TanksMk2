/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

/**
 * Indicates if the entity is dead.
 * 
 * <p>A dead entity has <em>minimal</em> functionality,
 * although it does still exist. There is no going back once
 * an entity is marked as dead, as some systems may purge
 * functionality completely (like {@link ModelViewState}).
 * 
 * @author codex
 */
public class Dead implements EntityComponent {

    public Dead() {}
    
}
