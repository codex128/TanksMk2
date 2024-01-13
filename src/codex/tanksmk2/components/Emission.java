/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

/**
 * Component signifying a single, abrupt emission of a set of particles.
 * 
 * @author codex
 */
public class Emission implements EntityComponent {
    
    public static final int ENTITY = 0, GROUP = 1, ENTITY_VOLUME = 2;
    
    private final Object key;
    private final int numParticles;
    private final int position;
    
    public Emission(int numParticles) {
        this(null, numParticles, GROUP);
    }
    public Emission(int numParticles, int position) {
        this(null, numParticles, position);
    }
    public Emission(Object key, int numParticles) {
        this(key, numParticles, GROUP);
    }
    public Emission(Object key, int numParticles, int position) {
        this.key = key;
        this.numParticles = numParticles;
        this.position = position;
    }
    
    public Object getKey() {
        return key;
    }
    public Object getKey(EntityId id) {
        if (key != null) {
            return key;
        } else {
            return id;
        }
    }    
    public int getNumParticles() {
        return numParticles;
    }
    
    /**
     * Indicates where the emitted particles should be spawned.
     * <p>
     * <ul>
     *  <li>{@link #ENTITY}: all particles are spawned at this entity's
     * world position.</li>
     *  <li>{@link #GROUP}: all particles are spawned using the particle
     * group's volume at the particle group's world position.</li>
     *  <li>{@link #ENTITY_VOLUME}: all particles are spawned using the
     * particle group's volume at this entity's world position.</li>
     * </ul>
     * default={@link #GROUP}
     * 
     * @return spawn position indicator
     */
    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Emission{" + "numParticles=" + numParticles + ", position=" + position + '}';
    }
    
}
