package com.webreach.mirth.client.ui;

import com.webreach.mirth.model.User;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.AlternateRowHighlighter;
import org.jdesktop.swingx.decorator.HighlighterPipeline;

public class Users extends javax.swing.JScrollPane 
{
    public JXTable usersTable;
    
    private Frame parent;
    
    /** Creates new form thisl */
    public Users() 
    {  
        this.parent = PlatformUI.MIRTH_FRAME;
        initComponents();
        setVisible(true);
    }
    
    private void initComponents()
    {
        makeUsersTable();
        this.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                deselectRows();
            }
        });
    }
    
    public void makeUsersTable()
    {
        usersTable = new JXTable();
        Object[][] tableData = null;
        tableData = new Object[parent.users.size()][3];
        
        for (int i=0; i < parent.users.size(); i++)
        {
            User temp = parent.users.get(i);

            tableData[i][0] = "" + temp.getId();
            tableData[i][1] = new CellData(new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/user.png")), temp.getUsername());
        } 
        
        usersTable.setModel(new javax.swing.table.DefaultTableModel(
            tableData,
            new String []
            {
                "User ID", "Username"
            }
        )
        {
            boolean[] canEdit = new boolean []
            {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        
        usersTable.setSelectionMode(0);
        
        usersTable.getColumnExt("Username").setCellRenderer(new ImageCellRenderer());        
        usersTable.getColumnExt("User ID").setMaxWidth(UIConstants.MAX_WIDTH);
        
        usersTable.getColumnExt("User ID").setCellRenderer(new CenterCellRenderer());
        usersTable.getColumnExt("User ID").setHeaderRenderer(PlatformUI.CENTER_COLUMN_HEADER_RENDERER); 
        
        usersTable.packTable(UIConstants.COL_MARGIN);
        
        usersTable.setRowHeight(20);
        usersTable.setOpaque(true);
        
        usersTable.setCellSelectionEnabled(false);
        usersTable.setRowSelectionAllowed(true);
        
        if(Preferences.systemNodeForPackage(Mirth.class).getBoolean("highlightRows", true))
        {
            HighlighterPipeline highlighter = new HighlighterPipeline();
            highlighter.addHighlighter(new AlternateRowHighlighter(UIConstants.HIGHLIGHTER_COLOR, UIConstants.BACKGROUND_COLOR, UIConstants.TITLE_TEXT_COLOR));
            usersTable.setHighlighters(highlighter);
        }
        
        this.setViewportView(usersTable);
        
        usersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent evt)
            {
                UsersListSelected(evt);
            }
        });
        usersTable.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                if (evt.getClickCount() >= 2)
                    parent.doEditUser();
            }
        });
        
    }    
    
    private void UsersListSelected(ListSelectionEvent evt) 
    {
        int row = usersTable.getSelectedRow();
        if(row >= 0 && usersTable.getSelectedColumn()>= 0)
        {
            parent.setVisibleTasks(parent.userTasks, 2, -1, true);
        }
    }
    
    public void deselectRows()
    {
        usersTable.clearSelection();
        parent.setVisibleTasks(parent.userTasks, 2, -1, false);
    }
    
    public int getSelectedRow()
    {
        return usersTable.getSelectedRow();
    }
    
    public boolean setSelectedUser(String userName)
    {
        int columnNumber = getColumnNumber("Username");
        for (int i = 0; i < parent.users.size(); i++)
        {
            if (userName.equals((String)(((CellData)usersTable.getValueAt(i, columnNumber)).getText())))
            {
                usersTable.setRowSelectionInterval(i,i);
                return true;
            }
        }
        return false;
    }
    
    public int getUserIndex()
    {
        int columnNumber = getColumnNumber("Username");
         
        if (usersTable.getSelectedRow() != -1)
        {
            String userName = ((CellData)usersTable.getValueAt(getSelectedRow(), columnNumber)).getText();

            for (int i=0; i < parent.users.size(); i++)
            {
                if(parent.users.get(i).getUsername().equals(userName))
                {
                    return i;
                }
            }
        }
        return UIConstants.ERROR_CONSTANT;
    }
    
    public int getColumnNumber(String name)
    {
        for (int i = 0; i < usersTable.getColumnCount(); i++)
        {
            if (usersTable.getColumnName(i).equalsIgnoreCase(name))
                return i;
        }
        return UIConstants.ERROR_CONSTANT;
    }
}