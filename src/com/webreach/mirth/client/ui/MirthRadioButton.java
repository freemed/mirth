package com.webreach.mirth.client.ui;


public class MirthRadioButton extends javax.swing.JRadioButton {
    private Frame parent;
    
    public MirthRadioButton() {
        super();
        this.parent = PlatformUI.MIRTH_FRAME;
        this.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButtonChanged(evt);
            }
        });
    }
    
    public void radioButtonChanged(java.awt.event.ActionEvent evt)
    {
        parent.enableSave();
    }
}