/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.collision;

import codex.tanksmk2.components.Bounces;
import codex.tanksmk2.components.Team;
import com.simsilica.es.CreatedBy;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import java.util.Objects;

/**
 * Interface for filtering shapes before collision.
 * 
 * @author codex
 */
public interface ShapeFilter {
    
    /**
     * Returns true if the filter confirms the shape.
     * 
     * @param ed entity data
     * @param user User entity of this filter. For bullet collision, this would be the bullet as it filters out which shapes to collide with.
     * @param shape entity id of the collision shape
     * @return true if shape is confirmed
     */
    public boolean filter(EntityData ed, EntityId user, EntityId shape);
    
    /*=================
     |  Quick Access  |
     =================*/
    
    /**
     * Filter that always confirms shapes.
     */
    public static final ShapeFilter OPEN = (EntityData ed, EntityId user, EntityId shape) -> true;
    
    /**
     * Filter that always rejects shapes.
     */
    public static final ShapeFilter CLOSED = (EntityData ed, EntityId user, EntityId shape) -> false;
    
    /**
     * Confirms the shape if it has the id.
     * 
     * @param id
     * @return 
     */
    public static ShapeFilter byId(EntityId id) {
        return new IdFilter(id);
    }
    
    /**
     * Confirms the shape if it doesn't have the id.
     * 
     * @param id
     * @return 
     */
    public static ShapeFilter notId(EntityId id) {
        return nor(new IdFilter(id));
    }
    
    /**
     * Confirms the shape if it is on the team.
     * <p>
     * Does not confirm shapes that do not have a {@link Team} component.
     * 
     * @param team team id
     * @return 
     */
    public static ShapeFilter byTeam(int team) {
        return new TeamFilter(team);
    }
    
    /**
     * Only confirms shapes if the number of bounces the user entity has
     * made is between the min and max values (inclusive).
     * <p>
     * Does not confirm any shapes if the user does not have
     * a {@link Bounces} component.
     * 
     * @param min minimum number of bounces, or -1 for no restriction
     * @param max maximum number of bounces, or -1 for no restriction
     * @return 
     */
    public static ShapeFilter byBounces(int min, int max) {
        return new BounceFilter(min, max);
    }
    
    /**
     * Confirms shapes created by the creator entity.
     * <p>
     * If the creator id is null, confirms shapes that are created by
     * the same entity that created the user. Does not confirm shapes
     * that were not created by another entity.
     * 
     * @param creator creator entity (can be null)
     * @return 
     */
    public static ShapeFilter byCreator(EntityId creator) {
        return new CreatorFilter(creator);
    }
    
    /**
     * Confirms shapes if all the delegate filters confirm them.
     * 
     * @param filters
     * @return 
     */
    public static ShapeFilter and(ShapeFilter... filters) {
        return new AndFilter(filters);
    }
    
    /**
     * Confirms shapes if at least one delegate filter confirms them.
     * 
     * @param filters
     * @return 
     */
    public static ShapeFilter or(ShapeFilter... filters) {
        return new OrFilter(filters);
    }
    
    /**
     * Confirms shapes if at least one delegate filter does not confirm them.
     * 
     * @param filters
     * @return 
     */
    public static ShapeFilter nand(ShapeFilter... filters) {
        return new NandFilter(filters);
    }
    
    /**
     * Confirms shapes if all delegate filters do not confirm them.
     * @param filters
     * @return 
     */
    public static ShapeFilter nor(ShapeFilter... filters) {
        return new NorFilter(filters);
    }
    
    /*===========================
     |  Filter Implementations  |
     ===========================*/
    
    public static class IdFilter implements ShapeFilter {
        
        private final EntityId id;
        
        private IdFilter(EntityId id) {
            this.id = id;
        }
        
        @Override
        public boolean filter(EntityData ed, EntityId user, EntityId shape) {
            return Objects.equals(id, shape);
        }
        
    }
    public static class TeamFilter implements ShapeFilter {

        private final int teamId;

        public TeamFilter(int teamId) {
            this.teamId = teamId;
        }
        
        @Override
        public boolean filter(EntityData ed, EntityId user, EntityId shape) {
            var team = ed.getComponent(shape, Team.class);
            return team != null && team.getId() == teamId;
        }
        
    }
    public static class BounceFilter implements ShapeFilter {
        
        private final int min, max;
        
        private BounceFilter(int min, int max) {
            this.min = min;
            this.max = max;
        }
        
        @Override
        public boolean filter(EntityData ed, EntityId user, EntityId shape) {
            var bounces = ed.getComponent(user, Bounces.class);
            return bounces != null
                    && (min < 0 || bounces.getBouncesMade() >= min)
                    && (max < 0 || bounces.getBouncesMade() <= max);
        }
        
    }
    public static class CreatorFilter implements ShapeFilter {

        private final EntityId creator;
        
        private CreatorFilter(EntityId creator) {
            this.creator = creator;
        }
        
        @Override
        public boolean filter(EntityData ed, EntityId user, EntityId shape) {
            var c = ed.getComponent(shape, CreatedBy.class);
            if (creator != null) {
                return c != null && c.getCreatorId().equals(creator);
            }
            else {
                var uc = ed.getComponent(user, CreatedBy.class);
                return c != null && uc != null && c.getCreatorId().equals(uc.getCreatorId());
            }
        }
        
    }
    public static class AndFilter implements ShapeFilter {

        private final ShapeFilter[] filters;

        private AndFilter(ShapeFilter[] filters) {
            this.filters = filters;
        }
        
        @Override
        public boolean filter(EntityData ed, EntityId user, EntityId shape) {
            for (var f : filters) {
                if (!f.filter(ed, user, shape)) return false;
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
        public boolean filter(EntityData ed, EntityId user, EntityId shape) {
            for (var f : filters) {
                if (f.filter(ed, user, shape)) return true;
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
        public boolean filter(EntityData ed, EntityId user, EntityId shape) {
            for (var f : filters) {
                if (!f.filter(ed, user, shape)) return true;
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
        public boolean filter(EntityData ed, EntityId user, EntityId shape) {
            for (var f : filters) {
                if (f.filter(ed, user, shape)) return false;
            }
            return true;
        }
        
    }
    
}
