package com.webreach.mirth.client.ui;

import java.awt.event.KeyEvent;

public class MirthTextPane extends javax.swing.JTextPane
{
    private Frame parent;

    public MirthTextPane()
    {
        super();
        this.parent = PlatformUI.MIRTH_FRAME;
    }
    
    public void processKeyEvent(KeyEvent ev)
    {
        parent.enableSave();
        super.processKeyEvent(ev);
    }
}
