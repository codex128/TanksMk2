/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.util;

import com.simsilica.es.ComponentFilter;
import com.simsilica.es.EntityComponent;
import java.util.function.Function;

/**
 *
 * @author codex
 * @param <T>
 */
public class FunctionFilter <T extends EntityComponent> implements ComponentFilter<T> {
    
    private final Class<T> type;
    private final Function<T, Boolean> eval;

    public FunctionFilter(Class<T> type, Function<T, Boolean> eval) {
        this.type = type;
        this.eval = eval;
    }
    
    @Override
    public Class<T> getComponentType() {
        return type;
    }
    @Override
    public boolean evaluate(EntityComponent c) {
        return c.getClass().equals(type) && eval.apply((T)c);
    }
    
}
