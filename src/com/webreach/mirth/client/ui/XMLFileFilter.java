package com.webreach.mirth.client.ui;

import java.io.File;

public class XMLFileFilter extends javax.swing.filechooser.FileFilter 
{
    // abstract method
    public boolean accept(File f) 
    {
        //if it is a directory -- we want to show it so return true.
        if (f.isDirectory()) 
            return true;

        //get the extension of the file
        String extension = getExtension(f);

        //check to see if the extension is equal to "xml"
        if (extension.equals("xml"))
            return true; 

        //default -- fall through. False is return on all
        //occasions except:
        //a) the file is a directory
        //b) the file's extension is what we are looking for.
        return false;
    }

    // abstract method
    public String getDescription() 
    {
        return "XML files";
    }

    // Method to get the extension of the file, in lowercase
    private String getExtension(File f) 
    {
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1) 
            return s.substring(i+1).toLowerCase();
        return "";
    }
}
