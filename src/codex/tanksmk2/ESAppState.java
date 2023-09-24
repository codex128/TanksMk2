/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2;

import codex.boost.GameAppState;
import com.jme3.app.Application;
import com.simsilica.es.EntityData;
import com.simsilica.state.GameSystemsState;

/**
 *
 * @author codex
 */
public abstract class ESAppState extends GameAppState {

    protected EntityData ed;
    
    @Override
    protected void initialize(Application app) {
        ed = getState(GameSystemsState.class).get(EntityData.class);
        super.initialize(app);
    }
    @Override
    public abstract void update(float tpf);
    
}
