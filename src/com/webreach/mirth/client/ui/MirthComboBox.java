package com.webreach.mirth.client.ui;


public class MirthComboBox extends javax.swing.JComboBox {
    private Frame parent;

    public MirthComboBox(Frame parent) {
        super();
        this.parent = parent;
        this.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxChanged(evt);
            }
        });
    }
    
    public void comboBoxChanged(java.awt.event.ActionEvent evt)
    {
        parent.channelEditTasks.getContentPane().getComponent(0).setVisible(true);
    }
}