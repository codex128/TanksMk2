/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.factories;

/**
 *
 * @author codex
 */
public class LootInfo {
    
    private final int number;
    private final Prefab prefab;
    private final Object[] arguments;

    public LootInfo(int number, Prefab prefab, Object... arguments) {
        this.number = number;
        this.prefab = prefab;
        this.arguments = arguments;
    }

    public int getNumber() {
        return number;
    }
    public Prefab getPrefab() {
        return prefab;
    }
    public Object[] getArguments() {
        return arguments;
    }
    
}
