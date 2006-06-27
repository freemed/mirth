package com.webreach.mirth.client.ui;

import org.jdesktop.swingx.JXList;

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
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                mirthListMouseDragged(evt);
            }
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
    
    private void mirthListMouseExited(java.awt.event.MouseEvent evt) {
        this.clearSelection();
    }
    
    private void mirthListMouseDragged(java.awt.event.MouseEvent evt) {
    }

    private void mirthListMouseMoved(java.awt.event.MouseEvent evt) {
        int index = this.locationToIndex(evt.getPoint());
        
        if (index != -1)
            this.setSelectedIndex(index);
    }
    
    
}
