package com.webreach.mirth.client.ui;

/** 
 * Mirth's implementation of the JComboBox.  Adds enabling of
 * the save button in parent.
 */
public class MirthComboBox extends javax.swing.JComboBox {
    private Frame parent;

    public MirthComboBox() {
        super();
        this.setFocusable(false);
        this.parent = PlatformUI.MIRTH_FRAME;
        this.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxChanged(evt);
            }
        });
    }
    
    public void comboBoxChanged(java.awt.event.ActionEvent evt)
    {
        parent.enableSave();
    }
}