/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codex.tanksmk2.gui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.DefaultRangedValueModel;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.ProgressBar;
import com.simsilica.lemur.RangedValueModel;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.core.VersionedReference;
import com.simsilica.lemur.style.Attributes;
import com.simsilica.lemur.style.ElementId;
import com.simsilica.lemur.style.StyleDefaults;
import com.simsilica.lemur.style.Styles;

/**
 *
 * @author codex
 */
public class GameProgressBar extends ProgressBar {
    
    public static final String CHASE_ID = "chase";
    
    private Panel chase;
    private float chaseSpeed = 2;

    public GameProgressBar() {
        this(new DefaultRangedValueModel(), true, new ElementId(ELEMENT_ID), null);
    }
    public GameProgressBar(String style) {
        this(new DefaultRangedValueModel(), true, new ElementId(ELEMENT_ID), style);
    }
    public GameProgressBar(ElementId elementId, String style) {
        this(new DefaultRangedValueModel(), true, elementId, style);
    } 
    public GameProgressBar(RangedValueModel model) {
        this(model, true, new ElementId(ELEMENT_ID), null);
    }
    public GameProgressBar(RangedValueModel model, String style) {
        this(model, true, new ElementId(ELEMENT_ID), style);
    }
    public GameProgressBar(RangedValueModel model, boolean applyStyles, ElementId elementId, String style) {
        super(model, false, elementId, style);
        chase = new Panel(getElementId().child(CHASE_ID), style);
        attachChild(chase);
        if (applyStyles) {
            GuiGlobals.getInstance().getStyles().applyStyles(this, getElementId(), style);
        }
    }
    
    @StyleDefaults(ProgressBar.ELEMENT_ID)
    public static void initializeDefaultStyles(Styles styles, Attributes attr) {
        ProgressBar.initializeDefaultStyles(styles, attr);
        ElementId parent = new ElementId(ProgressBar.ELEMENT_ID);
        styles.getSelector(parent.child(CHASE_ID), null).set("background", 
                new QuadBackgroundComponent(new ColorRGBA(0f, 1f, 0f, 1), 2, 2));
    }
    
    @Override
    public void updateLogicalState(float tpf) {
        super.updateLogicalState(tpf);
        Vector3f target = getValueIndicator().getSize();
        float current = chase.getSize().x;
        if (current-target.x <= chaseSpeed*tpf) {
            current = target.x;
        } else {
            current -= chaseSpeed*tpf*FastMath.sign(current-target.x);
        }
        chase.setSize(new Vector3f(current, target.y, target.z));
        chase.setLocalTranslation(getValueIndicator().getLocalTranslation().subtract(0, 0, 0.1f));
    }
    
}
