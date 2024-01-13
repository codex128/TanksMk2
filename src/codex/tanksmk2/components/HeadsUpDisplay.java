/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.components;

import codex.tanksmk2.util.FunctionFilter;
import com.simsilica.es.ComponentFilter;
import com.simsilica.es.EntityComponent;

/**
 * 
 * @author codex
 */
public class HeadsUpDisplay implements EntityComponent {
    
    public static final int
            TOP_LEFT = 0, TOP = 1, TOP_RIGHT = 2, LEFT = 3, CENTER = 4,
            RIGHT = 5, BOTTOM_LEFT = 6, BOTTOM = 7, BOTTOM_RIGHT = 8;
    
    private final String subject;
    private final int alignment;

    public HeadsUpDisplay(String subject, int alignment) {
        this.subject = subject;
        this.alignment = alignment;
    }

    public String getSubject() {
        return subject;
    }

    public int getAlignment() {
        return alignment;
    }

    @Override
    public String toString() {
        return "HeadsUpDisplay{" + "subject=" + subject + ", alignment=" + alignment + '}';
    }
    
    public static ComponentFilter<HeadsUpDisplay> filter(String subject) {
        return new FunctionFilter<>(HeadsUpDisplay.class, c -> c.subject.equals(subject));
    }
    public static ComponentFilter<HeadsUpDisplay> filter(String subject, int alignment) {
        return new FunctionFilter<>(HeadsUpDisplay.class, c -> c.subject.equals(subject) && c.alignment == alignment);
    }
    
}
