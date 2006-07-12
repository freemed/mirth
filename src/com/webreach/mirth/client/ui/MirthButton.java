package com.webreach.mirth.client.ui;

/** 
 * Mirth's implementation of the JButton.  Adds enabling of
 * the save button in parent.
 */
public class MirthButton extends javax.swing.JButton
{
    private Frame parent;
    
    public MirthButton() {
        super();
        this.parent = PlatformUI.MIRTH_FRAME;
        this.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPressed(evt);
            }
        });
    }
    
    public void buttonPressed(java.awt.event.ActionEvent evt)
    {
        parent.enableSave();
    }
}