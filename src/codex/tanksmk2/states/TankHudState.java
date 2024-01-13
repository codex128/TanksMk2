/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.states;

import codex.tanksmk2.ESAppState;
import codex.tanksmk2.components.AmmoChannel;
import codex.tanksmk2.components.HeadsUpDisplay;
import codex.tanksmk2.components.Health;
import codex.tanksmk2.components.Inventory;
import codex.tanksmk2.components.Stats;
import codex.tanksmk2.gui.GameProgressBar;
import codex.tanksmk2.util.Settings;
import com.jme3.app.Application;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityContainer;
import com.simsilica.es.EntityData;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Insets3f;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.ProgressBar;
import com.simsilica.lemur.component.IconComponent;
import com.simsilica.lemur.component.SpringGridLayout;
import com.simsilica.lemur.core.GuiControl;

/**
 *
 * @author codex
 */
public class TankHudState extends ESAppState {
    
    private static final int MAX_DISPLAY_VALUE = 999;
    
    private HudContainer hud;
    
    @Override
    protected void init(Application app) {
        hud = new HudContainer(ed);
    }
    @Override
    protected void cleanup(Application app) {}
    @Override
    protected void onEnable() {
        hud.start();
    }
    @Override
    protected void onDisable() {
        hud.stop();
    }
    @Override
    public void update(float tpf) {
        hud.update();
    }
    
    private String inventoryAmountDisplay(int n) {
        if (n >= 0) {
            return Integer.toString(Math.min(n, MAX_DISPLAY_VALUE));
        } else {
            return Integer.toString(MAX_DISPLAY_VALUE);
        }
    }
    
    private class TankHud {
        
        private final Entity entity;
        private Container gui;
        private ProgressBar health;
        private Label bullets, missiles, grenades;
        private int currentAlign = -1;

        public TankHud(Entity entity) {
            this.entity = entity;
            init();
        }
        
        private void init() {      
            
            gui = new Container();
            gui.setLayout(new SpringGridLayout(Axis.Y, Axis.X));
            gui.setLocalTranslation(5, windowSize.y-5, 0);
            guiNode.attachChild(gui);
            
            Settings<IconComponent> iconSettings = (IconComponent object) -> {
                object.setMargin(30, 0);
            };
            
            var bulletIcon = new IconComponent("Interface/icons/bullet-icon16.png");
            iconSettings.apply(bulletIcon);
            var missileIcon = new IconComponent("Interface/icons/bullet-icon16.png");
            iconSettings.apply(missileIcon);
            var grenadeIcon = new IconComponent("Interface/icons/bullet-icon16.png");
            iconSettings.apply(grenadeIcon);
            
            var inventory = gui.addChild(new Container());
            inventory.setLayout(new SpringGridLayout(Axis.Y, Axis.X));
            inventory.setBackground(null);
            bullets = inventory.addChild(new Label("0"), 0, 0);
            bullets.getControl(GuiControl.class).addComponent(bulletIcon);
            missiles = inventory.addChild(new Label("0"), 1, 0);
            missiles.getControl(GuiControl.class).addComponent(missileIcon);
            grenades = inventory.addChild(new Label("0"), 2, 0);
            grenades.getControl(GuiControl.class).addComponent(grenadeIcon);
            
            health = gui.addChild(new GameProgressBar("health-bar"));
            health.getModel().setMaximum(1);
            health.getModel().setMinimum(0);
            health.getLabel().setInsets(new Insets3f(0, 20, 0, 20));
            
            update();
            
        }
        private void update() {
            var h = entity.get(Health.class);
            health.setMessage((int)h.getHealth()+" / "+(int)h.getMaxHealth());
            health.setProgressPercent(h.getHealthPercent());
            var inv = entity.get(Inventory.class);
            bullets.setText(inventoryAmountDisplay(inv.get(Inventory.BULLETS)));
            missiles.setText(inventoryAmountDisplay(inv.get(Inventory.MISSILES)));
            grenades.setText(inventoryAmountDisplay(inv.get(Inventory.GRENADES)));
            int a = entity.get(HeadsUpDisplay.class).getAlignment();
            if (a != currentAlign) {
                align(a);
            }
        }
        private void terminate() {
            gui.removeFromParent();
        }
        
        private void align(int alignment) {
            currentAlign = alignment;
            switch (alignment) {
                case HeadsUpDisplay.TOP_LEFT -> {
                    
                }
                case HeadsUpDisplay.TOP_RIGHT -> {
                    
                }
                case HeadsUpDisplay.BOTTOM_LEFT -> {
                    
                }
                case HeadsUpDisplay.BOTTOM_RIGHT -> {
                    
                }
                default -> {
                    throw new UnsupportedOperationException("Tank HUD does not support alignment: "+alignment);
                }
            }
        }
        
    }
    private class HudContainer extends EntityContainer<TankHud> {

        public HudContainer(EntityData ed) {
            super(ed, HeadsUpDisplay.filter("tank"), HeadsUpDisplay.class, Health.class, Inventory.class, Stats.class, AmmoChannel.class);
        }
        
        @Override
        protected TankHud addObject(Entity entity) {
            return new TankHud(entity);
        }
        @Override
        protected void updateObject(TankHud t, Entity entity) {
            t.update();
        }
        @Override
        protected void removeObject(TankHud t, Entity entity) {
            t.terminate();
        }
        
    }
    
}
