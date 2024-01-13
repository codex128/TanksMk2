/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import codex.tanksmk2.util.FunctionFilter;
import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class ScopeIn implements EntityComponent {
    
    private final boolean scope;

    public ScopeIn(boolean scope) {
        this.scope = scope;
    }

    public boolean isScopeIn() {
        return scope;
    }
    
    public static FunctionFilter<ScopeIn> filter(boolean s) {
        return new FunctionFilter<>(ScopeIn.class, c -> c.scope == s);
    }
    
}
