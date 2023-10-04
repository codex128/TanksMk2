/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

import codex.tanksmk2.components.ContactResponse;
import codex.tanksmk2.events.ContactEvent;
import com.simsilica.es.EntityData;

/**
 *
 * @author codex
 */
public class ContactMethods {
    
    public static final String
            KILL_BULLET = "kill-bullet",
            REFLECT = "reflect",
            FORCE_REFLECT = "force-reflect",
            KILL_TARGET = "kill-target",
            DAMAGE_TARGET = "damage-target",
            INHERIT = "inherit-method";
    
    private final EntityData ed;
    
    public ContactMethods(EntityData ed) {
        this.ed = ed;
    }
    
    public void apply(ContactResponse response, ContactEvent event) {
        
    }
    
}
