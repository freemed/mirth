package com.webreach.mirth.client.ui.components;

import org.jdesktop.swingx.JXList;

/**
 * An implementation of JXList that has mouse rollover
 * selection implemented.
 */
public class MirthVariableList extends JXList {
    
    /**
     * Creates a new instance of MirthVariableList
     */
    public MirthVariableList()
    {
        super();
        this.setDragEnabled(true);
        this.setFocusable(false);
        this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                mirthListMouseMoved(evt);
            }
        });
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mirthListMouseExited(evt);
            }
        });
    }
    
    /**
     * When leaving the variable list, the selection is cleared.
     */
    private void mirthListMouseExited(java.awt.event.MouseEvent evt) {
        this.clearSelection();
    }

    /**
     * When moving on the variable list, set the selection to whatever
     * the mouse is over.
     */
    private void mirthListMouseMoved(java.awt.event.MouseEvent evt) {
        int index = this.locationToIndex(evt.getPoint());
        
        if (index != -1)
            this.setSelectedIndex(index);
    }
    
    
}
