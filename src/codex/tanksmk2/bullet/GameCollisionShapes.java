/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package codex.tanksmk2.bullet;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.simsilica.bullet.CollisionShapes;
import com.simsilica.bullet.ShapeInfo;
import com.simsilica.es.EntityData;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bare-bones CollisionShapes implementation.
 * 
 * This is designed to be queryable. If a class wants to know if a
 * collision shape exists, {@link #getShape(com.simsilica.bullet.ShapeInfo)} can be
 * used to do that. In {@link DefaultCollisionShapes}, this is impossible to do
 * without throwing an exception.
 * 
 * @author codex
 */
public class GameCollisionShapes implements CollisionShapes {
    
    private final EntityData ed;
    private final Map<Integer, CollisionShape> shapeIndex = new ConcurrentHashMap<>();

    public GameCollisionShapes(EntityData ed) {
        this.ed = ed;
    }
    
    @Override
    public CollisionShape register(ShapeInfo info, CollisionShape shape) {
        shapeIndex.put(info.getShapeId(), shape);
        return shape;
    }
    @Override
    public CollisionShape getShape(ShapeInfo shape) {
        return shapeIndex.get(shape.getShapeId());
    }
    
}
