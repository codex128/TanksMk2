/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.bots;

import codex.tanksmk2.components.BotUser;
import codex.tanksmk2.components.LookAt;
import codex.tanksmk2.components.Team;
import codex.tanksmk2.util.GameUtils;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.math.Transform;
import com.simsilica.es.Entity;

/**
 *
 * @author codex
 */
public class TurretBot extends Bot {
    
    private Entity target;
    private final Transform botTransform = new Transform();
    private final Transform targetTransform = new Transform();
    private final Transform tempTransform = new Transform();
    
    @Override
    public void preUpdate(BotUpdate update) {
        target = null;
        float dist = -1;
        GameUtils.getWorldTransform(update.ed, getUser(BotUser.MAIN).getId(), botTransform);
        Team team = GameUtils.getComponent(update.ed, getUser(BotUser.MAIN).getId(), Team.class, Team.UNSPECIFIED);
        for (var t : update.targets) {
            if (!t.get(Team.class).equals(team)) {
                float d = GameUtils.getWorldTransform(update.ed, t.getId(), tempTransform).getTranslation().distanceSquared(botTransform.getTranslation());
                if (target == null || d < dist) {
                    target = t;
                    targetTransform.set(tempTransform);
                    dist = d;
                }
            }
        }
    }
    
    @Override
    public void move(BotUpdate update) {
        // no movement
    }
    
    @Override
    public void aim(BotUpdate update) {
        update.ed.setComponent(getUser(BotUser.AIM).getId(), new LookAt(targetTransform.getTranslation().subtract(botTransform.getTranslation()).setY(0).normalizeLocal()));
    }
    
    @Override
    public void shoot(BotUpdate update) {}
    
    @Override
    public void postUpdate(BotUpdate update) {}
    
    @Override
    public void physicsUpdate(PhysicsSpace space, float timeStep, BotUpdate update) {}    
    
    @Override
    public boolean readyForUpdate() {
        return hasUsers(BotUser.MAIN, BotUser.AIM, BotUser.SHOOT);
    }
    
}
