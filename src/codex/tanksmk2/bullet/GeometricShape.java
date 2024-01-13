/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.bullet;

/**
 *
 * @author codex
 */
public enum GeometricShape {
    
    Box("Box"),
    DynamicMesh("DynamicMesh"),
    GImpact("GImpact"),
    MergedBox("MergedBox"),
    MergedHull("MergedHull"),
    MergedMesh("MergedMesh"),
    Mesh("Mesh"),
    Vhacd("Vhacd"),
    Vhacd4("Vhacd4");
    
    private final String name;
    private GeometricShape(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    /**
     * Version of {@link #valueOf(java.lang.Class, java.lang.String)} which
     * conforms to the userdata policy (lower-case first letter).
     * 
     * @param name
     * @return 
     */
    public static GeometricShape value(String name) {
        return valueOf(GeometricShape.class, Character.toUpperCase(name.charAt(0))+name.substring(1));
    }
    
}
