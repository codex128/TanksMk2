package codex.tanksmk2;

import codex.j3map.J3mapFactory;
import codex.j3map.processors.BooleanProcessor;
import codex.j3map.processors.FloatProcessor;
import codex.j3map.processors.IntegerProcessor;
import codex.j3map.processors.StringProcessor;
import codex.tanksmk2.bullet.TransformPublisher;
import codex.tanksmk2.systems.BuffSystem;
import codex.tanksmk2.systems.CameraState;
import codex.tanksmk2.systems.InventorySystem;
import codex.tanksmk2.systems.ModelViewState;
import codex.tanksmk2.systems.ParentSystem;
import codex.tanksmk2.systems.PipelineSystem;
import codex.tanksmk2.systems.PlayerInputState;
import codex.tanksmk2.systems.TankDriverSystem;
import codex.tanksmk2.systems.TankMovementSystem;
import codex.tanksmk2.systems.TankRotationSystem;
import codex.tanksmk2.systems.TrackingSystem;
import com.jme3.app.BasicProfilerState;
import com.jme3.app.DebugKeysAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.InputManager;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.simsilica.bullet.BulletSystem;
import com.simsilica.bullet.CollisionShapes;
import com.simsilica.bullet.Contact;
import com.simsilica.bullet.DefaultCollisionShapes;
import com.simsilica.bullet.DefaultContactPublisher;
import com.simsilica.bullet.ShapeInfo;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.base.DefaultEntityData;
import com.simsilica.es.common.Decay;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.sim.common.DecaySystem;
import com.simsilica.state.GameSystemsState;

/**
 * 
 * @author codex
 */
public class Main extends SimpleApplication {
    
    public static final String TITLE = "Tanks";
    
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
            new BasicProfilerState(),
            new GameSystemsState(false),
            new ModelViewState()
        );
    }
    
    @Override
    public void simpleInitApp() {
        
        GuiGlobals.initialize(this);
        
        assetManager.registerLoader(J3mapFactory.class, "stats", "j3map");
        J3mapFactory.registerAllProcessors(
            BooleanProcessor.class,
            StringProcessor.class,
            IntegerProcessor.class,
            FloatProcessor.class
        );
        
        systems = stateManager.getState(GameSystemsState.class);
        ed = systems.register(EntityData.class, new DefaultEntityData());
        systems.register(AssetManager.class, assetManager);
        systems.register(InputManager.class, inputManager);
        
        // hint: override destroyEntity(Entity e) to do extra stuff on entity removal
        systems.addSystem(new DecaySystem());
        
        var shapes = systems.register(CollisionShapes.class, new DefaultCollisionShapes(ed));
        shapes.register(ShapeInfo.create("tank", ed), CollisionShapeFactory.createDynamicMeshShape(assetManager.loadModel("Models/tank/tankCollisionShape.j3o")));
        shapes.register(ShapeInfo.create("floor", ed), new BoxCollisionShape(20f, .1f, 20f));
        
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
        
        systems.addSystem(new BuffSystem());
        systems.addSystem(new InventorySystem());
        systems.addSystem(new ParentSystem());
        systems.addSystem(new PipelineSystem());
        stateManager.attach(new PlayerInputState());
        systems.addSystem(new TankDriverSystem());
        systems.addSystem(new TankRotationSystem());
        systems.addSystem(new TankMovementSystem());
        systems.addSystem(new TrackingSystem());
        stateManager.attach(new CameraState());
        
        systems.addSystem(new LevelSystem());
        
        // test lighting
        rootNode.addLight(new DirectionalLight(new Vector3f(1, -1, 1)));
        
    }
    @Override
    public void simpleUpdate(float tpf) {}
    @Override
    public void simpleRender(RenderManager rm) {}
    
}
