package com.webreach.mirth.client.ui;


public class MirthCheckBox extends javax.swing.JCheckBox {
    private Frame parent;

    public MirthCheckBox() {
        super();
        this.parent = PlatformUI.MIRTH_FRAME;
        this.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxChanged(evt);
            }
        });
    }
    
    public void checkBoxChanged(java.awt.event.ActionEvent evt)
    {
        parent.enableSave();
    }
}