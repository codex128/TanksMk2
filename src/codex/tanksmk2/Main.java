package codex.tanksmk2;

import codex.tanksmk2.bullet.TransformPublisher;
import codex.tanksmk2.systems.ModelViewState;
import com.jme3.app.BasicProfilerState;
import com.jme3.app.DebugKeysAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.renderer.RenderManager;
import com.simsilica.bullet.BulletSystem;
import com.simsilica.bullet.CollisionShapes;
import com.simsilica.bullet.Contact;
import com.simsilica.bullet.DefaultCollisionShapes;
import com.simsilica.bullet.DefaultContactPublisher;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.base.DefaultEntityData;
import com.simsilica.es.common.Decay;
import com.simsilica.sim.common.DecaySystem;
import com.simsilica.state.GameSystemsState;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    EntityData ed;
    GameSystemsState systems;
    BulletSystem bullet;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    
    private Main() {
        super(
            new StatsAppState(),
            new DebugKeysAppState(),
            new BasicProfilerState(false),
            new GameSystemsState(),
            new ModelViewState()
        );
    }
    
    @Override
    public void simpleInitApp() {
        
        systems = stateManager.getState(GameSystemsState.class);
        ed = systems.register(EntityData.class, new DefaultEntityData());
        
        // hint: override destroyEntity(Entity e) to do extra stuff on entity removal
        systems.addSystem(new DecaySystem());
        
        var shapes = systems.register(CollisionShapes.class, new DefaultCollisionShapes(ed));
        
        bullet = new BulletSystem();
        bullet.addPhysicsObjectListener(new TransformPublisher(ed));
        bullet.addEntityCollisionListener(new DefaultContactPublisher(ed) {
            @Override
            protected EntityId createEntity(Contact c) {
                var result = ed.createEntity();
                ed.setComponents(result, c, Decay.duration(systems.getStepTime().getTime(), systems.getStepTime().toSimTime(0.1)));
                return result;
            }
        });
        systems.register(BulletSystem.class, bullet);
        
    }
    @Override
    public void simpleUpdate(float tpf) {}
    @Override
    public void simpleRender(RenderManager rm) {}
    
}
