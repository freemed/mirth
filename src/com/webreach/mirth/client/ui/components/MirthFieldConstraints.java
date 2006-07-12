package com.webreach.mirth.client.ui.components;
import javax.swing.text.*;

/** 
 * Document that can be set with certain field constraints. 
 */
public class MirthFieldConstraints extends PlainDocument
{
    private int limit;
    // optional uppercase conversion
    private boolean toUppercase = false;
    private boolean numbersOnly = false;

    /**
     * Constructor that sets a character number limit
     */
    public MirthFieldConstraints(int limit)
    {
        super();
        this.limit = limit;
    }
    
    /**
     * Constructor that sets a character number limit, uppercase conversion, and numbers only
     */
    public MirthFieldConstraints(int limit, boolean toUppercase, boolean numbersOnly)
    {
        super();
        this.limit = limit;
        this.toUppercase = toUppercase;
        this.numbersOnly = numbersOnly;
    }

    /**
     * Overwritten insertString method to check if the string should
     * actually be inserted based on the constraints.
     */
    public void insertString(int offset, String  str, AttributeSet attr) throws BadLocationException
    {
        if (str == null)
            return;
        if ((getLength() + str.length()) <= limit)
        {
            if (toUppercase)
                str = str.toUpperCase();
            if (numbersOnly)
            {
                try
                {
                    if (Double.isNaN(Double.parseDouble(str)))
                        return;
                }
                catch(Exception e)
                {
                    return;
                }
            }
            super.insertString(offset, str, attr);
        }
    }
}
