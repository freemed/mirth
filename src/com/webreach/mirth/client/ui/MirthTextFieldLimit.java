package com.webreach.mirth.client.ui;
import javax.swing.text.*;

public class MirthTextFieldLimit extends PlainDocument
{
    private int limit;
    // optional uppercase conversion
    private boolean toUppercase = false;
    private boolean numbersOnly = false;

    MirthTextFieldLimit(int limit)
    {
        super();
        this.limit = limit;
    }
    
    MirthTextFieldLimit(int limit, boolean toUppercase, boolean numbersOnly)
    {
        super();
        this.limit = limit;
        this.toUppercase = toUppercase;
        this.numbersOnly = numbersOnly;
    }


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
