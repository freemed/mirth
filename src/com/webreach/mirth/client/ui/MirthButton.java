package com.webreach.mirth.client.ui;


public class MirthButton extends javax.swing.JButton {
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
        parent.channelEditTasks.getContentPane().getComponent(0).setVisible(true);
    }
}