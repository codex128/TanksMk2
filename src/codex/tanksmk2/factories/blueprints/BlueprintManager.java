/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories.blueprints;

import codex.tanksmk2.factories.Factory;
import codex.tanksmk2.factories.Prefab;
import com.simsilica.es.EntityData;
import com.simsilica.sim.SimTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author codex
 */
public class BlueprintManager {
    
    private final EntityData ed;
    private final SimTime time;
    private final ConcurrentHashMap<String, Factory<Blueprint>> blueprints = new ConcurrentHashMap<>();

    public BlueprintManager(EntityData ed, SimTime time) {
        this.ed = ed;
        this.time = time;
    }
    
    public void registerLoader(String name, Factory<Blueprint> loader) {
        blueprints.put(name, loader);
    }
    
    public Blueprint loadBlueprint(String name) {
        return loadBlueprint(name, false);
    }
    public Blueprint loadBlueprint(String name, boolean failOnMiss) {
        var factory = fetchLoader(name, failOnMiss);
        if (factory != null) {
            return factory.create();
        } else {
            return null;
        }
    }
    public <T extends Blueprint> T loadBlueprint(String name, Class<T> type) {
        return loadBlueprint(name, type, false);
    }
    public <T extends Blueprint> T loadBlueprint(String name, Class<T> type, boolean failOnMiss) {
        var factory = fetchLoader(name, failOnMiss);
        var print = factory.create();
        if (type.isAssignableFrom(print.getClass())) {
            print.setEntityData(ed);
            print.setTime(time);
            return (T)print;
        } else if (failOnMiss) {
            throw new NullPointerException("Blueprint loader failed to construct "+type.getSimpleName()+".");
        } else {
            return null;
        }
    }
    
    private Factory<Blueprint> fetchLoader(String name, boolean failOnMiss) {
        var factory = blueprints.get(name);
        if (factory == null) {
            if (failOnMiss) {
                throw new NullPointerException("Failed to locate blueprint loader under \""+name+"\".");
            } else {
                return null;
            }
        }
        return factory;
    }
    
}
