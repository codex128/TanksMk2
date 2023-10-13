/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.collision;

import codex.tanksmk2.components.Team;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import java.util.Objects;

/**
 *
 * @author codex
 */
public interface ShapeFilter {
    
    /**
     * Returns true if the filter confirms the shape.
     * 
     * @param ed entity data
     * @param shape entity id of the collision shape
     * @return true if shape is confirmed
     */
    public boolean filter(EntityData ed, EntityId shape);
    
    public static ShapeFilter byId(EntityId id) {
        return new IdFilter(id);
    }
    public static ShapeFilter notId(EntityId id) {
        return nor(new IdFilter(id));
    }
    public static ShapeFilter byTeam(int team) {
        return new TeamFilter(team);
    }
    public static ShapeFilter and(ShapeFilter... filters) {
        return new AndFilter(filters);
    }
    public static ShapeFilter or(ShapeFilter... filters) {
        return new OrFilter(filters);
    }
    public static ShapeFilter nand(ShapeFilter... filters) {
        return new NandFilter(filters);
    }
    public static ShapeFilter nor(ShapeFilter... filters) {
        return new NorFilter(filters);
    }
    
    public static class IdFilter implements ShapeFilter {
        
        private final EntityId id;
        
        private IdFilter(EntityId id) {
            this.id = id;
        }
        
        @Override
        public boolean filter(EntityData ed, EntityId shape) {
            return Objects.equals(id, shape);
        }
        
    }
    public static class TeamFilter implements ShapeFilter {

        private final int teamId;

        public TeamFilter(int teamId) {
            this.teamId = teamId;
        }
        
        @Override
        public boolean filter(EntityData ed, EntityId shape) {
            var team = ed.getComponent(shape, Team.class);
            return team != null && team.getId() == teamId;
        }
        
    }
    public static class AndFilter implements ShapeFilter {

        private final ShapeFilter[] filters;

        private AndFilter(ShapeFilter[] filters) {
            this.filters = filters;
        }
        
        @Override
        public boolean filter(EntityData ed, EntityId shape) {
            for (var f : filters) {
                if (!f.filter(ed, shape)) return false;
            }
            return true;
        }
        
    }
    public static class OrFilter implements ShapeFilter {

        private final ShapeFilter[] filters;

        private OrFilter(ShapeFilter[] filters) {
            this.filters = filters;
        }
        
        @Override
        public boolean filter(EntityData ed, EntityId shape) {
            for (var f : filters) {
                if (f.filter(ed, shape)) return true;
            }
            return false;
        }
        
    }
    public static class NandFilter implements ShapeFilter {

        private final ShapeFilter[] filters;

        private NandFilter(ShapeFilter[] filters) {
            this.filters = filters;
        }
        
        @Override
        public boolean filter(EntityData ed, EntityId shape) {
            for (var f : filters) {
                if (!f.filter(ed, shape)) return true;
            }
            return false;
        }
        
    }
    public static class NorFilter implements ShapeFilter {

        private final ShapeFilter[] filters;

        public NorFilter(ShapeFilter[] filters) {
            this.filters = filters;
        }
        
        @Override
        public boolean filter(EntityData ed, EntityId shape) {
            for (var f : filters) {
                if (f.filter(ed, shape)) return false;
            }
            return true;
        }
        
    }
    
}
