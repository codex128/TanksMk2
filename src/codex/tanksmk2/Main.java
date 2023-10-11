package codex.tanksmk2;

import codex.j3map.J3mapFactory;
import codex.j3map.processors.*;
import codex.tanksmk2.bullet.*;
import codex.tanksmk2.factories.*;
import codex.tanksmk2.systems.*;
import com.jme3.app.BasicProfilerState;
import com.jme3.app.DebugKeysAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.debug.DebugConfiguration;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.InputManager;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import com.simsilica.bullet.BulletSystem;
import com.simsilica.bullet.CollisionShapes;
import com.simsilica.bullet.Contact;
import com.simsilica.bullet.DefaultContactPublisher;
import com.simsilica.bullet.ShapeInfo;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.base.DefaultEntityData;
import com.simsilica.es.common.Decay;
import com.simsilica.event.ErrorEvent;
import com.simsilica.event.EventBus;
import com.simsilica.event.EventListener;
import com.simsilica.event.EventType;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.sim.GameSystem;
import com.simsilica.sim.SimEvent;
import com.simsilica.sim.common.DecaySystem;
import com.simsilica.state.GameSystemsState;

/**
 * 
 * @author codex
 */
public class Main extends SimpleApplication implements EventListener<ErrorEvent> {
    
    public static final String TITLE = "Tanks";
    public static final boolean ENABLE_BULLET_DEBUG = true;
    
    EntityData ed;
    ModelViewState view;
    GameSystemsState background;
    BulletSystem bullet;
    
    public static void main(String[] args) {
        Main app = new Main();
        var settings = new AppSettings(true);
        settings.setResolution(1024, 768);
        settings.setTitle(TITLE);
        app.setSettings(settings);
        app.start();
    }
    
    private Main() {
        super(
            new StatsAppState(),
            new DebugKeysAppState(),
            new BasicProfilerState()
        );
    }
    
    @Override
    public void simpleInitApp() {
        
        EventBus.addListener(ErrorEvent.fatalError, this);
        
        GuiGlobals.initialize(this);
        
        assetManager.registerLoader(J3mapFactory.class, "stats", "j3map");
        J3mapFactory.registerAllProcessors(
            BooleanProcessor.class,
            StringProcessor.class,
            IntegerProcessor.class,
            FloatProcessor.class
        );
        
        view = new ModelViewState();
        background = new GameSystemsState(true);        
        stateManager.attachAll(view, background);
        
        ed = register(EntityData.class, new DefaultEntityData());
        register(AssetManager.class, assetManager);
        register(InputManager.class, inputManager);
        register(RenderManager.class, renderManager);
        register(EntityFactory.class, new EntityFactory(ed, background.getGameSystemManager()));
        
        // hint: override destroyEntity(Entity e) to do extra stuff on entity removal
        background.addSystem(new DecaySystem());
        
        var shapes = register(CollisionShapes.class, new GameCollisionShapes(ed));
        shapes.register(ShapeInfo.create("tank", ed), CollisionShapeFactory.createDynamicMeshShape(assetManager.loadModel("Models/tank/tankCollisionShape.j3o")));
        shapes.register(ShapeInfo.create("floor", ed), new BoxCollisionShape(20f, .1f, 20f));
        
        bullet = new BulletSystem();
        bullet.addPhysicsObjectListener(new TransformPublisher(ed));
        bullet.addPhysicsObjectListener(new VelocityPublisher(ed));
        bullet.addEntityCollisionListener(new DefaultContactPublisher(ed) {
            @Override
            protected EntityId createEntity(Contact c) {
                var result = ed.createEntity();
                ed.setComponents(result, c, Decay.duration(background.getStepTime().getTime(), background.getStepTime().toSimTime(0.1)));
                return result;
            }
        });
        register(BulletSystem.class, bullet);
        
        // bullet visual debug
        if (ENABLE_BULLET_DEBUG) {
            var config = new DebugConfiguration();
            config.setViewPorts(viewPort);
            var debug = new BulletDebugAdapter(config);
            EventBus.addListener(SimEvent.simInitialized, debug);
            stateManager.attach(debug);
        }
        
        stateManager.attach(new LightingState());
        stateManager.attach(new PlayerInputState());
        stateManager.attach(new CameraState());
        
        addSystems(background,
            new BuffSystem(),
            new InventorySystem(),
            new ParentSystem(),
            new PipelineSystem(),
            new TankDriverSystem(),
            new TankRotationSystem(),
            new TrackingSystem(),
            new ShootingSystem(),
            //new SpeedStatSystem(),
            new FaceVelocitySystem(),
            new BulletMotionSystem(),
            new WheelSystem()
        );        
        background.addSystem(new LevelSystem());
        
        // test lighting
        rootNode.addLight(new DirectionalLight(new Vector3f(1, -1, 1)));
        
    }
    @Override
    public void simpleUpdate(float tpf) {}
    @Override
    public void simpleRender(RenderManager rm) {}    
    @Override
    public void newEvent(EventType<ErrorEvent> type, ErrorEvent event) {
        System.err.println("A fatal error has occured, forcing the application to shut down.");
        event.getError().printStackTrace(System.err);
        stop();
    }
    
    private <T> T register(Class<T> type, T object) {
        return background.register(type, object);
    }
    private void addSystems(GameSystemsState state, GameSystem... systems) {
        for (var s : systems) {
            state.addSystem(s);
        }
    }
    
}
