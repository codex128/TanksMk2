/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;

/**
 *
 * @author codex
 */
public class ContactResponse implements EntityComponent {
    
    private final int[] responses;

    public ContactResponse(int... responses) {
        this.responses = responses;
    }

    public int[] getResponses() {
        return responses;
    }
    public String[] getResponseNames(EntityData ed) {
        String[] names = new String[responses.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = ed.getStrings().getString(responses[i]);
        }
        return names;
    }
    @Override
    public String toString() {
        return "ContactResponse{" + "responses=" + responses.length + '}';
    }
    
    public static ContactResponse create(EntityData ed, String... responses) {
        int[] ids = new int[responses.length];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = ed.getStrings().getStringId(responses[i], true);
        }
        return new ContactResponse(ids);
    }
    
}
