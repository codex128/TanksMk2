/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import com.simsilica.es.EntityComponent;

/**
 *
 * @author codex
 */
public class BotId implements EntityComponent {
    
    public static final int GRAY = 0, TURRET = 1;
    
    private static int nextId = 0;
    private final int id, type;

    public BotId(int id, int type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }
    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "BotId{" + "id=" + id + ", type=" + type + '}';
    }
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.id;
        hash = 89 * hash + this.type;
        return hash;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BotId other = (BotId)obj;
        return this.id == other.id && this.type == other.type;
    }
    
    
    public static BotId create(int type) {
        return new BotId(nextId++, type);
    }
    
}
