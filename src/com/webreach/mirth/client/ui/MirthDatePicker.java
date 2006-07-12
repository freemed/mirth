package com.webreach.mirth.client.ui;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import org.jdesktop.swingx.JXDatePicker;

/** 
 * Mirth's implementation of the JXDatePicker.  Sets the format, 
 * editor font, and button image.
 */
public class MirthDatePicker extends JXDatePicker {
    
    /**
     * Creates a new instance of MirthDatePicker
     */
    public MirthDatePicker()
    {
        super();
        this.setFocusable(false);
        setFormats(new String[] { "EEE MM-dd-yyyy" });
        getEditor().setFont(UIConstants.TEXTFIELD_PLAIN_FONT);
        UIManager.put("JXDatePicker.arrowDown.image", new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/calendar_view_month.png")));
        // must call updateUI() so that the first mirthDatePicker uses this button image.
        updateUI();
    }    
}
