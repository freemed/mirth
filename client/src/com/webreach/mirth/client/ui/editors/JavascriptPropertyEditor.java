package com.webreach.mirth.client.ui.editors;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.net.*;

import javax.swing.*;

 
public class JavascriptPropertyEditor extends PropertyEditorSupport
{
    /**
     * Returns <code>true</code>.
     *
     * @return <code>true</code>
     */
    public boolean supportsCustomEditor()
    {
        return true;
    }
    
    /**
     * Returns the custom property editor.
     */
    public Component getCustomEditor()
    {
        JPanel fieldPanel = new JPanel(new BorderLayout());
        
        JTextField scriptField = new JTextField();
        
        if(getValue()==null)
        {
            scriptField.setText("");
        }
        else
        {
            scriptField.setText(getAsText());
        }
        
        scriptField.addActionListener(
            new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    JTextField tf = (JTextField)ae.getSource();

                    String val = tf.getText().trim();
                    
                    if(val.length()>0)
                    {
                        setValue(tf.getText());
                    }
                    else
                    {
                        setValue(null);
                    }
                    
                    firePropertyChange();
                }
            }
        );
        
        JButton scriptButton = new JButton("...");
        
        scriptButton.addActionListener(
            new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    JButton button = (JButton)ae.getSource();
                    
                    JDialog dialog = new JDialog();
                    
                    setValue("testScript");
                    
                    firePropertyChange();
                    
                    
//                    
//                    SelectURLDialog sud;
//                    
//                    Window win = SwingUtilities.windowForComponent(button);
//                    
//                    if(win instanceof Frame)
//                    {
//                        sud = new SelectURLDialog((Frame)win);
//                    }
//                    else // win instanceof Dialog
//                    {
//                        sud = new SelectURLDialog((Dialog)win);
//                    }
//                    
//                    sud.setLocation(
//                        button.getLocationOnScreen().x+16,
//                        button.getLocationOnScreen().y+16
//                    );
//                                        
//                    if(sud.showDialog((URL)getValue()))
//                    {
//                        setValue(sud.getSelectedURL());
//                        
//                        firePropertyChange();
//                    }
                }
            }
        );
        
        fieldPanel.add(scriptField, BorderLayout.CENTER);
        
        fieldPanel.add(scriptButton, BorderLayout.EAST);
        
        return fieldPanel;
    }
}
