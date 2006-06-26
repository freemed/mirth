package com.webreach.mirth.client.ui;

import com.webreach.mirth.client.core.Client;
import com.webreach.mirth.client.core.ClientException;
import com.webreach.mirth.client.ui.browsers.event.EventBrowser;
import com.webreach.mirth.client.ui.browsers.message.MessageBrowser;
import com.webreach.mirth.model.Channel;
import com.webreach.mirth.model.ChannelStatus;
import com.webreach.mirth.model.User;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import org.jdesktop.swingx.*;
import org.jdesktop.swingx.action.*;

/**
 *
 * @author  brendanh
 */
public class Frame extends JXFrame
{
    public Client mirthClient;
    
    public StatusPanel statusListPage;
    public ChannelPanel channelListPage;
    public AdminPanel adminPanel;
    public ChannelSetup channelEditPage;
    public JXTaskPaneContainer taskPaneContainer;
    
    protected List<Channel> channels;
    protected List<User> users;
    protected List<ChannelStatus> status;
    
    protected ActionManager manager = ActionManager.getInstance();
    protected JPanel contentPane;
    protected BorderLayout borderLayout1 = new BorderLayout();
    protected JLabel statusBar = new JLabel();
    protected JSplitPane jSplitPane1 = new JSplitPane();
    protected JScrollPane jScrollPane1 = new JScrollPane();
    protected JScrollPane jScrollPane2 = new JScrollPane();
    protected Component currentContentPage = null;
    protected JXTaskPaneContainer currentTaskPaneContainer = null;
    
    protected JXTaskPane viewPane;
    protected JXTaskPane otherPane;
    protected JXTaskPane settingsTasks;
    protected JXTaskPane channelTasks;
    protected JXTaskPane statusTasks;
    protected JXTaskPane eventTasks;
    protected JXTaskPane messageTasks;    
    protected JXTaskPane details;
    protected JXTaskPane channelEditTasks;
    protected JXTaskPane userTasks;
    
    protected ArrayList<ConnectorClass> sourceConnectors;
    protected ArrayList<ConnectorClass> destinationConnectors; 
    
    private Thread statusUpdater;     
    
    public Frame(Client mirthClient)
    {
        this.mirthClient = mirthClient;
        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        statusUpdater = new Thread(new StatusUpdater(this));
        statusUpdater.start();
        
        try
        {
            channels = this.mirthClient.getChannels();
            users = this.mirthClient.getUsers();
            status = this.mirthClient.getChannelStatusList();
        }
        catch (ClientException ex)
        {
            ex.printStackTrace();
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        
        sourceConnectors = new ArrayList<ConnectorClass>();
        sourceConnectors.add(new DatabaseReader(this));
        sourceConnectors.add(new DatabaseWriter(this));
        sourceConnectors.add(new EmailSender(this));
        sourceConnectors.add(new FileWriter(this));
        sourceConnectors.add(new HTTPListener(this));
        sourceConnectors.add(new HTTPSListener(this));
        sourceConnectors.add(new LLPListener(this));
        sourceConnectors.add(new LLPSender(this));
        
        destinationConnectors = new ArrayList<ConnectorClass>();
        destinationConnectors.add(new DatabaseReader(this));
        destinationConnectors.add(new DatabaseWriter(this));
        destinationConnectors.add(new EmailSender(this));
        destinationConnectors.add(new FileWriter(this));
        destinationConnectors.add(new HTTPListener(this));
        destinationConnectors.add(new HTTPSListener(this));
        destinationConnectors.add(new LLPListener(this));
        destinationConnectors.add(new LLPSender(this));
        
        taskPaneContainer = new JXTaskPaneContainer();
        
        statusListPage = new StatusPanel(this);
        channelListPage = new ChannelPanel(this);
        adminPanel = new AdminPanel(this);
        channelEditPage = new ChannelSetup(this);

        try
        {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception
    {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(null);
        setSize(new Dimension(800, 450));
        setTitle("Mirth Client Prototype");
        statusBar.setText(" ");
        jSplitPane1.setDividerSize(3);
        contentPane.add(statusBar, BorderLayout.SOUTH);
        contentPane.add(jSplitPane1, java.awt.BorderLayout.CENTER);
        jSplitPane1.add(jScrollPane2, JSplitPane.RIGHT);
        jSplitPane1.add(jScrollPane1, JSplitPane.LEFT);
        jScrollPane1.setMinimumSize(new Dimension(170,0));
        jSplitPane1.setDividerLocation(170);
        setCurrentContentPage(statusListPage);
        makePaneContainer();
        setCurrentTaskPaneContainer(taskPaneContainer);
    }

    public void setupChannel(Channel channel)
    {
        setCurrentContentPage(channelEditPage);
        setBold(viewPane,Constants.ERROR_CONSTANT);
        setFocus(channelEditTasks);
        setVisibleTasks(channelEditTasks, 0, -1, false);
        channelEditPage.addChannel(channel);
    }

    public void setCurrentContentPage(Component contentPageObject)
    {
        if (contentPageObject==currentContentPage)
            return;
        
        if (currentContentPage!=null)
            jScrollPane2.getViewport().remove(currentContentPage);
        
        jScrollPane2.getViewport().add(contentPageObject);
        currentContentPage = contentPageObject;
    }
    
    public void setCurrentTaskPaneContainer(JXTaskPaneContainer container)
    {
        if (container==currentTaskPaneContainer)
            return;
        
        if (currentTaskPaneContainer!=null)
            jScrollPane1.getViewport().remove(currentTaskPaneContainer);
        
        jScrollPane1.getViewport().add(container);
        currentTaskPaneContainer = container;
    }
    
    private void makePaneContainer()
    {
        // Create Action pane
        viewPane = new JXTaskPane();
        viewPane.setTitle("Mirth Views");
        viewPane.setFocusable(false);
        viewPane.add(initActionCallback("doShowStatusPanel",ActionFactory.createBoundAction("showStatusPanel","Status Panel","S"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/status.png"))));
        viewPane.add(initActionCallback("doShowChannel",ActionFactory.createBoundAction("showChannelPannel","Channels","C"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/channel.png"))));
        viewPane.add(initActionCallback("doShowAdminPage",ActionFactory.createBoundAction("adminPage","Administration","A"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/admin.png"))));
        setNonFocusable(viewPane);
        taskPaneContainer.add(viewPane);

        // Create Settings Tasks Pane
        settingsTasks = new JXTaskPane();
        settingsTasks.setTitle("Settings Tasks");
        settingsTasks.setFocusable(false);
        settingsTasks.add(initActionCallback("doSaveSettings",ActionFactory.createBoundAction("doSaveSettings","Save Settings", "E"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/save.png"))));
        setNonFocusable(settingsTasks);
        taskPaneContainer.add(settingsTasks);

        // Create Channel Tasks Pane
        channelTasks = new JXTaskPane();
        channelTasks.setTitle("Channel Tasks");
        channelTasks.setFocusable(false);
        channelTasks.add(initActionCallback("doRefreshChannels",ActionFactory.createBoundAction("doRefreshChannels","Refresh", "R"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/refresh.png"))));
        channelTasks.add(initActionCallback("doDeployAll",ActionFactory.createBoundAction("doDeployAll","Deploy All", "P"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/deployall.png"))));
        channelTasks.add(initActionCallback("doNewChannel",ActionFactory.createBoundAction("doNewChannel","New Channel", "N"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/add.png"))));
        channelTasks.add(initActionCallback("doEditChannel",ActionFactory.createBoundAction("doEditChannel","Edit Channel", "E"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/edit.png"))));
        channelTasks.add(initActionCallback("doDeleteChannel",ActionFactory.createBoundAction("doDeleteChannel","Delete Channel","D"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/delete.png"))));
        channelTasks.add(initActionCallback("doEnable",ActionFactory.createBoundAction("doEnable","Enable", "B"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/start.png"))));
        channelTasks.add(initActionCallback("doDisable",ActionFactory.createBoundAction("doDisable","Disable", "L"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/stop.png"))));
        setNonFocusable(channelTasks);
        setVisibleTasks(channelTasks, 3, -1, false);
        taskPaneContainer.add(channelTasks);

        // Create Channel Edit Tasks Pane
        channelEditTasks = new JXTaskPane();
        channelEditTasks.setTitle("Channel Tasks");
        channelEditTasks.setFocusable(false);
        channelEditTasks.add(initActionCallback("doSaveChanges",ActionFactory.createBoundAction("doSaveChanges","Save Changes", "H"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/save.png"))));
        channelEditTasks.add(initActionCallback("doNewDestination",ActionFactory.createBoundAction("doNewDestination","New Destination", "N"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/add.png"))));
        channelEditTasks.add(initActionCallback("doDeleteDestination",ActionFactory.createBoundAction("doDeleteDestination","Delete Destination", "D"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/delete.png"))));
        channelEditTasks.add(initActionCallback("doEditTransformer",ActionFactory.createBoundAction("doEditTransformer","Edit Transformer", "E"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/edit.png"))));
        channelEditTasks.add(initActionCallback("doEditFilter",ActionFactory.createBoundAction("doEditFilter","Edit Filter", "F"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/edit.png"))));
        setNonFocusable(channelEditTasks);
        setVisibleTasks(channelEditTasks, 0, -1, false);
        taskPaneContainer.add(channelEditTasks);

        // Create Status Tasks Pane
        statusTasks = new JXTaskPane();
        statusTasks.setTitle("Status Tasks");
        statusTasks.setFocusable(false);
        statusTasks.add(initActionCallback("doRefresh",ActionFactory.createBoundAction("doRefresh","Refresh", "R"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/refresh.png"))));
        statusTasks.add(initActionCallback("doStartAll",ActionFactory.createBoundAction("doStartAll","Start All Channels", "T"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/start1.png"))));
        statusTasks.add(initActionCallback("doShowEvents",ActionFactory.createBoundAction("doShowEvents","Events", "E"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/logs.png"))));
        statusTasks.add(initActionCallback("doShowMessages",ActionFactory.createBoundAction("doShowMessages","Messages", "M"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/messages.png"))));
        statusTasks.add(initActionCallback("doStart",ActionFactory.createBoundAction("doStart","Start Channel", "L"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/start.png"))));
        statusTasks.add(initActionCallback("doPause",ActionFactory.createBoundAction("doPause","Pause Channel", "P"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/pause.png"))));
        statusTasks.add(initActionCallback("doStop",ActionFactory.createBoundAction("doStop","Stop Channel", "O"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/stop.png"))));
        //statusTasks.add(initActionCallback("doShowStats",ActionFactory.createBoundAction("doShowStats","Stats", "T"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/stats.png"))));
        setNonFocusable(statusTasks);
        setVisibleTasks(statusTasks, 3, -1, false);
        taskPaneContainer.add(statusTasks);
        
        // Create Event Tasks Pane
        eventTasks = new JXTaskPane();
        eventTasks.setTitle("Event Tasks");
        eventTasks.setFocusable(false);
        eventTasks.add(initActionCallback("doRefresh",ActionFactory.createBoundAction("doRefresh","Refresh", "R"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/refresh.png"))));
        setNonFocusable(eventTasks);
        setVisibleTasks(eventTasks, 1, -1, false);
        taskPaneContainer.add(eventTasks);
        
        // Create Message Tasks Pane
        messageTasks = new JXTaskPane();
        messageTasks.setTitle("Message Tasks");
        messageTasks.setFocusable(false);
        messageTasks.add(initActionCallback("doRefresh",ActionFactory.createBoundAction("doRefresh","Refresh", "R"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/refresh.png"))));
        setNonFocusable(messageTasks);
        setVisibleTasks(messageTasks, 1, -1, false);
        taskPaneContainer.add(messageTasks);

        // Create User Tasks Pane
        userTasks = new JXTaskPane();
        userTasks.setTitle("User Tasks");
        userTasks.setFocusable(false);
        userTasks.add(initActionCallback("doRefreshUser",ActionFactory.createBoundAction("doRefreshUser","Refresh", "R"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/refresh.png"))));
        userTasks.add(initActionCallback("doNewUser",ActionFactory.createBoundAction("doNewChannel","New User", "N"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/add.png"))));
        userTasks.add(initActionCallback("doEditUser",ActionFactory.createBoundAction("doEditChannel","Edit User", "E"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/edit.png"))));
        userTasks.add(initActionCallback("doDeleteUser",ActionFactory.createBoundAction("doDeleteChannel","Delete User","D"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/delete.png"))));
        setNonFocusable(userTasks);
        setVisibleTasks(userTasks, 2, -1, false);
        taskPaneContainer.add(userTasks);

        //Create Other Pane
        otherPane = new JXTaskPane();
        otherPane.setTitle("Other");
        otherPane.setFocusable(false);
        otherPane.add(initActionCallback("doDisconnect",ActionFactory.createBoundAction("doDisconnect","Disconnect","I"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/disconnect.png"))));
        otherPane.add(initActionCallback("goToAbout",ActionFactory.createBoundAction("goToAbout","About Mirth","B"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/about.png"))));
        otherPane.add(initActionCallback("goToMirth",ActionFactory.createBoundAction("goToMirth","Visit MirthProject.org","V"), new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/home.png"))));
        setNonFocusable(otherPane);
        taskPaneContainer.add(otherPane);

        // Create Details Pane
        details = new JXTaskPane();
        details.setTitle("Details");
        taskPaneContainer.add(details);
        setNonFocusable(details);
        details.setVisible(false);

        doShowStatusPanel();
    }

    private BoundAction initActionCallback(String callbackMethod,BoundAction boundAction, ImageIcon icon)
    {
        if(icon != null)
            boundAction.putValue(Action.SMALL_ICON, icon);
        boundAction.registerCallback(this,callbackMethod);
        return boundAction;
    }

    public void goToMirth()
    {
        BareBonesBrowserLaunch.openURL("http://www.mirthproject.org/");
    }

    public void goToAbout()
    {
        Frame_AboutBox dlg = new Frame_AboutBox(this);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
                        (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.pack();
        dlg.setVisible(true);
    }

    public void doShowStatusPanel()
    {
        if (!confirmLeaveChannelEditor())
            return;
        
        doRefresh();
        setBold(viewPane, 0);
        setCurrentContentPage(statusListPage);
        setFocus(statusTasks);
    }

    public void doShowChannel()
    {
        if (!confirmLeaveChannelEditor())
            return;
        
        doRefreshChannels();
        setBold(viewPane, 1);
        setCurrentContentPage(channelListPage);
        setFocus(channelTasks);
        channelListPage.deselectRows();
    }

    public void doShowAdminPage()
    {
        if (!confirmLeaveChannelEditor())
            return;
        
        setBold(viewPane, 2);
        setCurrentContentPage(adminPanel);
        adminPanel.showTasks();
    }

    public void doDisconnect()
    {
        this.dispose();
        Mirth.main(new String[0]);
    }

    public void doNewChannel()
    {
        ChannelWizard channelWizard = new ChannelWizard(this);
        Dimension channelWizardSize = channelWizard.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        channelWizard.setLocation((frmSize.width - channelWizardSize.width) / 2 + loc.x, (frmSize.height - channelWizardSize.height) / 2 + loc.y);
        channelWizard.setModal(true);
        channelWizard.setResizable(false);
        channelWizard.setVisible(true);
    }

    public void doEditChannel()
    {
        doRefreshChannels();

        if (channelListPage.getSelectedChannel() == Constants.ERROR_CONSTANT)
            JOptionPane.showMessageDialog(this, "Channel no longer exists.");
        else
        {
            setBold(viewPane, Constants.ERROR_CONSTANT);
            setCurrentContentPage(channelEditPage);
            setFocus(channelEditTasks);
            setVisibleTasks(channelEditTasks, 0, -1, false);
            channelEditPage.editChannel(channelListPage.getSelectedChannel());
        }
    }

    public void doDeleteChannel()
    {
        if(!alertUser("Are you sure you want to delete this channel?"))
            return;
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try
        {
            mirthClient.removeChannel(channels.get(channelListPage.getSelectedChannel()).getId());
            channels = mirthClient.getChannels();
            channelListPage.makeChannelTable();
        }
        catch (ClientException e)
        {
            e.printStackTrace();
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        doShowChannel();
    }

    public void doRefreshChannels()
    {
        int channelId = Constants.ERROR_CONSTANT;
        String channelName = null;

        if(channelListPage.getSelectedChannel() != Constants.ERROR_CONSTANT)
            channelId = channels.get(channelListPage.getSelectedChannel()).getId();
        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try
        {
            channels = mirthClient.getChannels();
            channelListPage.makeChannelTable();

            for(int i = 0; i<channels.size(); i++)
            {
                if(channelId == channels.get(i).getId())
                    channelName = channels.get(i).getName();
            }
        }
        catch (ClientException ex)
        {
            ex.printStackTrace();
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        
        // as long as the channel was not deleted
        if (channelName != null)
            channelListPage.setSelectedChannel(channelName);
    }
    
    public void doRefresh()
    {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try
        {
            status = mirthClient.getChannelStatusList();
            statusListPage.makeStatusTable();
        }
        catch (ClientException ex)
        {
            ex.printStackTrace();
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
    
    public void doStartAll()
    {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try
        {
            for(int i = 0; i<status.size(); i++)
            {
                if(status.get(i).getState() == ChannelStatus.State.STOPPED)
                    mirthClient.startChannel(status.get(i).getChannelId());
                else if(status.get(i).getState() == ChannelStatus.State.PAUSED)
                    mirthClient.resumeChannel(status.get(i).getChannelId());
            }
        } 
        catch (ClientException ex)
        {
            ex.printStackTrace();
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        
        doRefresh();   
    }
    
    public void doStart()
    {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try
        {
            if(status.get(statusListPage.getSelectedStatus()).getState() == ChannelStatus.State.STOPPED)
                mirthClient.startChannel(status.get(statusListPage.getSelectedStatus()).getChannelId());
            else if(status.get(statusListPage.getSelectedStatus()).getState() == ChannelStatus.State.PAUSED)
                mirthClient.resumeChannel(status.get(statusListPage.getSelectedStatus()).getChannelId());
        } 
        catch (ClientException ex)
        {
            ex.printStackTrace();
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        
        doRefresh();
    }
    
    public void doStop()
    {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try
        {
            mirthClient.stopChannel(status.get(statusListPage.getSelectedStatus()).getChannelId());
        } 
        catch (ClientException ex)
        {
            ex.printStackTrace();
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        
        doRefresh();
    }
    
    public void doPause()
    {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try
        {
            mirthClient.pauseChannel(status.get(statusListPage.getSelectedStatus()).getChannelId());
        } 
        catch (ClientException ex)
        {
            ex.printStackTrace();
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        
        doRefresh();
    }
    
    public void doNewDestination()
    {
        channelEditPage.addNewDestination();
    }
    
    public void doDeleteDestination()
    {
        if(!alertUser("Are you sure you want to delete this destination?"))
            return;
        
        channelEditPage.deleteDestination();
    }
    
    public void doEnable()
    {
       doRefreshChannels();
       
        if (channelListPage.getSelectedChannel() == Constants.ERROR_CONSTANT)
            JOptionPane.showMessageDialog(this, "Channel no longer exists.");
        else
        {
            Channel channel = channels.get(channelListPage.getSelectedChannel());
            channel.setEnabled(true);
            updateChannel(channel);
            channelListPage.deselectRows();
            channelListPage.setSelectedChannel(channel.getName());
        }
    }
    
    public void doDisable()
    {
        doRefreshChannels();

        if (channelListPage.getSelectedChannel() == Constants.ERROR_CONSTANT)
            JOptionPane.showMessageDialog(this, "Channel no longer exists.");
        else
        {
            Channel channel = channels.get(channelListPage.getSelectedChannel());
            channel.setEnabled(false);
            updateChannel(channel);
            channelListPage.deselectRows();
            channelListPage.setSelectedChannel(channel.getName());
        }
    }

    public void doNewUser()
    {
        UserWizard userWizard = new UserWizard(this, Constants.ERROR_CONSTANT);
        Dimension userWizardSize = userWizard.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        userWizard.setLocation((frmSize.width - userWizardSize.width) / 2 + loc.x, (frmSize.height - userWizardSize.height) / 2 + loc.y);
        userWizard.setModal(true);
        userWizard.setResizable(false);
        userWizard.setVisible(true);
    }

    public void doEditUser()
    {
        doRefreshUser();

        if (adminPanel.u.getUserIndex() == Constants.ERROR_CONSTANT)
            JOptionPane.showMessageDialog(this, "Users no longer exists.");
        else
        {
            UserWizard userDialog = new UserWizard(this, adminPanel.u.getSelectedRow());
            Dimension dialogSize = userDialog.getPreferredSize();
            Dimension frmSize = getSize();
            Point loc = getLocation();
            userDialog.setLocation((frmSize.width - dialogSize.width) / 2 + loc.x, (frmSize.height - dialogSize.height) / 2 + loc.y);
            userDialog.setResizable(false);
            userDialog.setVisible(true);
        }
    }

    public void doDeleteUser()
    {
        if(!alertUser("Are you sure you want to delete this user?"))
            return;
        
        int userToDelete = adminPanel.u.getUserIndex();
        String userName = (String) adminPanel.u.usersTable.getValueAt(adminPanel.u.getSelectedRow(), adminPanel.u.getColumnNumber("Username"));
        
        if(userName.equalsIgnoreCase("admin")) 
        {
           JOptionPane.showMessageDialog(this, "You cannot delete the admin.");
           return;
        }
        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try
        {
           if(userToDelete != Constants.ERROR_CONSTANT) 
           {
                mirthClient.removeUser(users.get(userToDelete).getId());
                users = mirthClient.getUsers();
                adminPanel.u.makeUsersTable();
                adminPanel.u.deselectRows();
           }
        }
        catch (ClientException ex)
        {
            ex.printStackTrace();
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void doRefreshUser()
    {
        int userId = Constants.ERROR_CONSTANT;
        String userName = null;

        if(adminPanel.u.getUserIndex() != Constants.ERROR_CONSTANT)
            userId = users.get(adminPanel.u.getUserIndex()).getId();
        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try
        {
            users = mirthClient.getUsers();
            adminPanel.u.makeUsersTable();

            for(int i = 0; i<users.size(); i++)
            {
                if(userId == users.get(i).getId())
                    userName = users.get(i).getUsername();
            }
        }
        catch (ClientException ex)
        {
            ex.printStackTrace();
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        // as long as the channel was not deleted
        if (userName != null)
            adminPanel.u.setSelectedUser(userName);
    }

    public void doDeployAll()
    {
        doRefreshChannels();
        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try
        {
            mirthClient.deployChannels();
        }
        catch (ClientException ex)
        {
            ex.printStackTrace();
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void doSaveChanges()
    {
        if (channelEditPage.saveChanges())
            channelEditTasks.getContentPane().getComponent(0).setVisible(false);
    }

    public void doShowMessages()
    {
        setBold(viewPane, Constants.ERROR_CONSTANT);
        setCurrentContentPage(new MessageBrowser(this));
        setFocus(messageTasks);
    }

    public void doShowEvents()
    {
        setBold(viewPane, Constants.ERROR_CONSTANT);
        setCurrentContentPage(new EventBrowser(this));
        setFocus(eventTasks);
    }

    public void doShowStats()
    {
        new Stats(this, statusListPage.statusTable.getSelectedRow());
    }

    public void setBold(JXTaskPane pane, int index)
    {
        for (int i=0; i<pane.getContentPane().getComponentCount(); i++)
            pane.getContentPane().getComponent(i).setFont(new Font("Tahoma",Font.PLAIN,11));
        
        if (index != Constants.ERROR_CONSTANT)
            pane.getContentPane().getComponent(index).setFont(new Font("Tahoma",Font.BOLD,11));
    }

    public void setFocus(JXTaskPane pane)
    {
        channelTasks.setVisible(false);
        channelEditTasks.setVisible(false);
        statusTasks.setVisible(false);
        eventTasks.setVisible(false);
        messageTasks.setVisible(false);
        settingsTasks.setVisible(false);
        userTasks.setVisible(false);
        pane.setVisible(true);
    }

    public void setNonFocusable(JXTaskPane pane)
    {
        for (int i=0; i<pane.getContentPane().getComponentCount(); i++)
            pane.getContentPane().getComponent(i).setFocusable(false);
    }

    public void setVisibleTasks(JXTaskPane pane, int startIndex, int endIndex, boolean visible)
    {
        if(endIndex == -1)
        {
            for (int i=startIndex; i < pane.getContentPane().getComponentCount(); i++)
                pane.getContentPane().getComponent(i).setVisible(visible);
        }
        else
        {
            for (int i=startIndex; (i <= endIndex) || (i < pane.getContentPane().getComponentCount()); i++)
                pane.getContentPane().getComponent(i).setVisible(visible);
        }
    }

    public boolean confirmLeaveChannelEditor()
    {
        if (channelEditTasks.getContentPane().getComponent(0).isVisible())
        {
            int option = JOptionPane.showConfirmDialog(this, "Would you like to save the channel changes?");
            if (option == JOptionPane.YES_OPTION)
            {
                if (!channelEditPage.saveChanges())
                    return false;
            }
            else if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION)
                return false;
        }
        channelEditTasks.getContentPane().getComponent(0).setVisible(false);
        return true;
    }

    public void updateChannel(Channel curr)
    {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try
        {
            mirthClient.updateChannel(curr, true);
            channels = mirthClient.getChannels();
            channelListPage.makeChannelTable();
        }
        catch (ClientException ex)
        {
            ex.printStackTrace();
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void updateUser(User curr)
    {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try
        {
            mirthClient.updateUser(curr);
            users = mirthClient.getUsers();
            adminPanel.u.makeUsersTable();
        }
        catch (ClientException ex)
        {
            ex.printStackTrace();
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
    
    public void doEditTransformer()
    {
        channelEditPage.editTransformer();
    }
    
    public void doEditFilter()
    {
        channelEditPage.editFilter();
    }
    
    public void doSaveSettings()
    {
        
    }
    
    public boolean alertUser(String message)
    {
        int option = JOptionPane.showConfirmDialog(this, message);
        if (option == JOptionPane.YES_OPTION)
            return true;
        else
            return false;
    }
}

