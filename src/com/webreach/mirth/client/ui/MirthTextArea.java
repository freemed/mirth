package com.webreach.mirth.client.ui;

import java.awt.event.KeyEvent;

public class MirthTextArea extends javax.swing.JTextArea
{
    private Frame parent;

    public MirthTextArea()
    {
        super();
        this.parent = PlatformUI.MIRTH_FRAME;
    }
    
    public void processKeyEvent(KeyEvent ev)
    {
        parent.channelEditTasks.getContentPane().getComponent(0).setVisible(true);
        super.processKeyEvent(ev);
    }
}
