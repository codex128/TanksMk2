/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.mesh;

import com.jme3.math.Vector2f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.simsilica.lemur.Axis;

/**
 *
 * @author codex
 */
public class QuadMesh extends Mesh {
    
    public QuadMesh(float w, float h) {
        updateGeometry(0, 0, w, h, Axis.Y, false);
    }
    public QuadMesh(float w, float h, Axis normal) {
        updateGeometry(0, 0, w, h, normal, false);
    }
    public QuadMesh(float w, float h, Axis normal, boolean flipNormals) {
        updateGeometry(0, 0, w, h, normal, flipNormals);
    }
    public QuadMesh(float x, float y, float w, float h) {
        updateGeometry(x, y, w, h, Axis.Y, false);
    }
    public QuadMesh(float x, float y, float w, float h, Axis normal) {
        updateGeometry(x, y, w, h, normal, false);
    }
    public QuadMesh(float x, float y, float w, float h, Axis normal, boolean flipNormals) {
        updateGeometry(x, y, w, h, normal, flipNormals);
    }
    
    private void updateGeometry(float x, float y, float w, float h, Axis normal, boolean flipNormals) {
        if (x < 0 || x > 1 || y < 0 || y > 1) {
            throw new IllegalArgumentException("X and Y values must be between 0 and 1 (inclusive).");
        }
        Vector2f p = new Vector2f(w*(1f-x), h*(1f-y));
        Vector2f n = new Vector2f(-w*x, -h*y);
        float[] verts = switch (normal) {
            case X -> new float[] {0, n.y, n.x, 0, p.y, n.x, 0, p.y, p.x, 0, n.y, p.x};
            case Y -> new float[] {n.x, 0, n.y, n.x, 0, p.y, p.x, 0, p.y, p.x, 0, n.y};
            case Z -> new float[] {n.x, n.y, 0, n.x, p.y, 0, p.x, p.y, 0, p.x, n.y, 0};
        };
        int norm = (!flipNormals ? 1 : -1);
        float[] normals = switch (normal) {
            case X -> new float[] {norm, 0, 0, norm, 0, 0};
            case Y -> new float[] {0, norm, 0, 0, norm, 0};
            case Z -> new float[] {0, 0, norm, 0, 0, norm};
        };
        float[] uvs = {0, 0, 0, 1, 1, 1, 1, 0};
        int[] faces;
        if (!flipNormals) {
            faces = new int[] {0, 3, 1, 2, 1, 3};
        } else {
            faces = new int[] {0, 1, 3, 2, 3, 1};
        }
        setBuffer(VertexBuffer.Type.Position, 3, verts);
        setBuffer(VertexBuffer.Type.Normal, 3, normals);
        setBuffer(VertexBuffer.Type.TexCoord, 2, uvs);
        setBuffer(VertexBuffer.Type.Index, 3, faces);
        updateBound();
        setStatic();
    }
    
}
