/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.mesh;

import codex.tanksmk2.collision.RaySegmentIterator;
import codex.tanksmk2.collision.Raycaster;
import codex.tanksmk2.collision.SegmentedRaycast;
import codex.tanksmk2.collision.ShapeFilter;
import codex.tanksmk2.components.Bounces;
import codex.tanksmk2.components.KillBulletOnTouch;
import codex.tanksmk2.components.LaserInfo;
import codex.tanksmk2.components.ReflectOnTouch;
import codex.tanksmk2.util.GameUtils;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

/**
 *
 * @author codex
 */
public class Laser extends Node {
    
    private static FloatBuffer verts = BufferUtils.createFloatBuffer(new float[] {
       -1, 0, 0,
       -1, 0, 1,
        1, 0, 1,
        1, 0, 0,
    });
    private static FloatBuffer uvs = BufferUtils.createFloatBuffer(new float[] {
        0, 0,
        0, 1,
        1, 1,
        1, 0,
    });
    private static IntBuffer indices = BufferUtils.createIntBuffer(new int[] {
        0, 3, 1,
        3, 2, 1,
    });
    
    private final Entity entity;
    private final Raycaster raycaster;
    private Material material;
    private final ArrayList<Segment> segments = new ArrayList<>();
    private final ArrayList<Vector3f> points = new ArrayList<>();
    private final float flickerSpeed = 1f;
    
    public Laser(Entity entity, Raycaster raycaster) {
        super("laser");
        this.entity = entity;
        this.raycaster = raycaster;
    }
    
    public void loadMaterial(AssetManager assetManager) {
        if (material == null) {
            material = new Material(assetManager, "MatDefs/laser.j3md");
            //material.setBoolean("Glow", true);
            material.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        }
    }
    public void cast(EntityData ed) {
        points.clear();
        var ray = GameUtils.getRayFromTransform(ed, entity.getId(), null);
        RaySegmentIterator iterator = new SegmentedRaycast(ed, raycaster, entity.getId(), ray).iterator();
        iterator.getRaycast().setFilter(ShapeFilter.Open);
        points.add(iterator.getRaycast().getRay().origin);
        while (iterator.hasNext()) {
            if (iterator.getNumIterations() >= entity.get(Bounces.class).getBouncesRemaining()) {
                break;
            }
            if (iterator.getNumIterations() > 0) {
                iterator.advanceProbePosition(SegmentedRaycast.ADV_DIST);
            }
            iterator.next();
            points.add(iterator.getContactPoint());
            if (iterator.collisionOccured()) {
                var id = iterator.getClosestResult().getCollisionEntity();
                var kill = ed.getComponent(id, KillBulletOnTouch.class);
                if (kill != null) {
                    break;
                }
                var reflect = ed.getComponent(id, ReflectOnTouch.class);
                if (reflect != null) {
                    iterator.setNextDirection(GameUtils.reflect(iterator.getNextDirection(), iterator.getClosestResult().getCollisionNormal()));
                }
            }
        }
    }
    public void applyChanges() {
        int i = 0;
        for (; i < points.size()-1; i++) {
            if (i < segments.size()) {
                updateSegmentPoints(segments.get(i), points.get(i), points.get(i+1));
            } else {
                var s = createSegment(points.get(i), points.get(i+1));
                segments.add(s);
                attachChild(s.geometry);
            }
        }
        while (i < segments.size()) {
            detachChild(segments.remove(i).geometry);
        }
    }
    public void update(float tpf) {
        for (var s : segments) {
            s.matValue += flickerSpeed*tpf;
            s.geometry.getMaterial().setFloat("FlickerAdv", s.matValue);
        }
    }
    
    private Segment createSegment(Vector3f p1, Vector3f p2) {
        if (material == null) {
            throw new NullPointerException("Must load material before applying changes.");
        }
        var g = new Geometry("laser-segment", createMesh());
        g.setMaterial(material);
        var s = new Segment(g);
        updateSegmentPoints(s, p1, p2);
        return s;
    }
    private void updateSegmentPoints(Segment segment, Vector3f p1, Vector3f p2) {
        segment.geometry.setLocalTranslation(p1);
        segment.geometry.setLocalRotation(new Quaternion().lookAt(p2.subtract(p1), Vector3f.UNIT_Y));
        float length = p1.distance(p2);
        segment.geometry.setLocalScale(entity.get(LaserInfo.class).getThickness(), 1, length);
        segment.geometry.getMaterial().setFloat("Length", length);
    }
    private Mesh createMesh() {
        var mesh = new Mesh();
        mesh.setBuffer(VertexBuffer.Type.Position, 3, verts);
        mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, uvs);
        mesh.setBuffer(VertexBuffer.Type.Index, 3, indices);
        return mesh;
    }
    
    public Entity getEntity() {
        return entity;
    }
    public ArrayList<Vector3f> getPointsList() {
        return points;
    }
    
    private static final class Segment {
        
        Geometry geometry;
        float matValue = 0f;
        
        Segment(Geometry geometry) {
            this.geometry = geometry;
        }
        
    }
    
}
