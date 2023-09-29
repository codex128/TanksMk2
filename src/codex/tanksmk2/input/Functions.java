/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package codex.tanksmk2.input;

import com.simsilica.lemur.input.FunctionId;
import com.simsilica.lemur.input.InputDevice;
import com.simsilica.lemur.input.InputMapper;

/**
 *
 * @author codex
 */
public interface Functions {
    
    public default void initialize(InputMapper im) {
        if (!isInitialized()) {
            initializeFunctionIds(GroupIdManager.getNextId());
        }
    }
    public default boolean isInitialized() {
        return getGroupName() != null;
    }
    public default void cleanupMappings(InputMapper im) {
        for (var f : getFunctions()) {
            clearMappingsForFunction(im, f);
        }
    }
    public default void clearMappingsForFunction(InputMapper im, FunctionId f) {
        for (var m : im.getMappings(f)) {
            im.removeMapping(m);
        }
    }
    
    /**
     * Initialize FunctionIds.
     * @param group 
     */
    public void initializeFunctionIds(String group);
    
    /**
     * Initialize default mappings.
     * @param im 
     */
    public void initializeDefaultMappings(InputMapper im);
    
    /**
     * Get the group name all FunctionIds are initialized to.
     * @return 
     */
    public String getGroupName();
    
    /**
     * Get all FunctionIds.
     * @return 
     */
    public FunctionId[] getFunctions();
    
    /**
     * Get the device this set of functions is associated with.
     * @return 
     */
    public InputDevice getDevice();
    
    
    public static class GroupIdManager {
        
        private static long nextGroupId = 0;
        
        private static String getNextId() {
            return "PlayerInputGroup["+(nextGroupId++)+"]";
        }
        
    }
    
}
