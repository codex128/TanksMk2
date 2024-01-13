/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.collision;

import codex.boost.scene.SceneGraphIterator;
import codex.tanksmk2.ESAppState;
import codex.tanksmk2.collision.ShapeFilter;
import codex.tanksmk2.util.GameUtils;
import com.jme3.app.Application;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.simsilica.es.EntityId;
import java.util.LinkedList;

/**
 *
 * @author codex
 */
public class SpatialContactSpace extends ESAppState {
    
    private final LinkedList<Spatial> spatials = new LinkedList<>();
    
    @Override
    protected void init(Application app) {}
    @Override
    protected void cleanup(Application app) {}
    @Override
    protected void onEnable() {}
    @Override
    protected void onDisable() {}
    @Override
    public void update(float tpf) {}
    
    public CollisionResult raycast(Ray ray, CollisionResults results, EntityId user, ShapeFilter filter) {
        if (results == null) {
            results = new CollisionResults();
        }
        for (var root : spatials) {
            var iterator = new SceneGraphIterator(root);
            for (var s : iterator) {
                var id = GameUtils.fetchId(s, 0);
                if (id == null) {
                    continue;
                }
                if (!filter.filter(ed, user, id)) {
                    iterator.ignoreChildren();
                    continue;
                }
                if (s instanceof Geometry) {
                    s.collideWith(ray, results);
                }
            }
        }
        if (results.size() > 0) {
            return results.getClosestCollision();
        } else {
            return null;
        }
    }
    
}
