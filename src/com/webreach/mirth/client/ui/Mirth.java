package com.webreach.mirth.client.ui;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.DesertBlue;
import com.jgoodies.looks.plastic.theme.Silver;
import com.webreach.mirth.client.core.Client;
import com.webreach.mirth.client.core.ClientException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import org.jdesktop.swingx.JXLoginPanel;
import org.jdesktop.swingx.auth.LoginEvent;
import org.jdesktop.swingx.auth.LoginListener;
import org.jdesktop.swingx.auth.LoginService;
import org.jdesktop.swingx.plaf.LookAndFeelAddons;
import org.jdesktop.swingx.plaf.aqua.AquaLookAndFeelAddons;
import org.jdesktop.swingx.plaf.metal.MetalLookAndFeelAddons;
import org.jdesktop.swingx.plaf.windows.WindowsLookAndFeelAddons;

/**
 * <p>Title: Mirth Beta Prototype</p>
 *
 * <p>Description: Mirth Beta Prototype</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: WebReach, Inc.</p>
 *
 * @author Brendan Haverlock and Jacob Brauer
 * @version 1.0
 */
public class Mirth 
{
    public Client client;
    private static Preferences userPreferences; 
    /**
     * Construct and show the application.
     */
    public Mirth(Client m) 
    {
        PlatformUI.MIRTH_FRAME = new Frame();
        PlatformUI.CENTER_COLUMN_HEADER_RENDERER.setHorizontalAlignment(SwingConstants.CENTER);
        PlatformUI.CENTER_COLUMN_HEADER_RENDERER.setDownIcon(UIManager.getIcon("ColumnHeaderRenderer.downIcon"));
        PlatformUI.CENTER_COLUMN_HEADER_RENDERER.setUpIcon(UIManager.getIcon("ColumnHeaderRenderer.upIcon"));
        
        userPreferences = Preferences.systemNodeForPackage(Mirth.class);
        
        JWindow splashWindow = new JWindow();
        splashWindow.getContentPane().add(new JLabel(new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/mirthlogo.gif"))), BorderLayout.CENTER);
        splashWindow.pack();
        splashWindow.setLocationRelativeTo(null);
        splashWindow.setVisible(true);
 
        PlatformUI.MIRTH_FRAME.setupFrame(m);
        
        splashWindow.dispose();
        
        int width = 900;
        int height = 700;
        
        if(userPreferences.getInt("maximizedState", PlatformUI.MIRTH_FRAME.MAXIMIZED_BOTH) != PlatformUI.MIRTH_FRAME.MAXIMIZED_BOTH)
        {
            width = userPreferences.getInt("width", 900);    
            height = userPreferences.getInt("height", 700);
        }
        
        PlatformUI.MIRTH_FRAME.setSize(width,height);
        PlatformUI.MIRTH_FRAME.setLocationRelativeTo(null);
        
        if(userPreferences.getInt("maximizedState", PlatformUI.MIRTH_FRAME.MAXIMIZED_BOTH) == PlatformUI.MIRTH_FRAME.MAXIMIZED_BOTH)
            PlatformUI.MIRTH_FRAME.setExtendedState(PlatformUI.MIRTH_FRAME.MAXIMIZED_BOTH);
        
        PlatformUI.MIRTH_FRAME.setVisible(true);
        PlatformUI.MIRTH_FRAME.addComponentListener(new java.awt.event.ComponentAdapter() 
        {
            public void componentResized(ComponentEvent e) 
            {
               Frame tmp = (Frame)e.getSource();
               if (tmp.getWidth()<900) 
               {
                 tmp.setSize(900, tmp.getHeight());
               }
               if (tmp.getHeight()<700) 
               {
                 tmp.setSize(tmp.getWidth(), 700);
               }
            }
        });
    }
    
    /**
     * Application entry point.
     *
     * @param args String[]
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    PlasticLookAndFeel.setPlasticTheme(new Silver());
                    UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
                    UIManager.put("win.xpstyle.name", "metallic");
                    LookAndFeelAddons.setAddon(WindowsLookAndFeelAddons.class);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                
                String userDefault = "admin";
                String passwordDefault = "abc12345";
                String mirthServerDefault = "https://127.0.0.1:8443";
                
                UIManager.put("JXLoginPanel.banner.foreground", UIConstants.TITLE_TEXT_COLOR);
                UIManager.put("JXLoginPanel.banner.darkBackground", UIConstants.BANNER_DARK_BACKGROUND);
                UIManager.put("JXLoginPanel.banner.lightBackground", UIConstants.BANNER_LIGHT_BACKGROUND);
                
                
                final MirthLoginService svc = new MirthLoginService();
                JXLoginPanel panel = new JXLoginPanel(svc, null, null, null);
                
                panel.setBannerText("");
                PlatformUI.BACKGROUND_IMAGE = new ImageIcon(panel.getUI().getBanner());
                
                panel.setBannerText("Login :: Mirth");
                panel.setOpaque(true);
                JPanel loginInfo = (JPanel)((JPanel)panel.getComponent(1)).getComponent(1);

                loginInfo.removeAll();
                
                String CLASS_NAME = JXLoginPanel.class.getCanonicalName(); 
                JLabel serverLabel = new JLabel("Server");
                javax.swing.JTextField serverName = new javax.swing.JTextField(mirthServerDefault, 30); 
                JLabel nameLabel = new JLabel("Login"); 
                JLabel passwordLabel = new JLabel("Password");  
                javax.swing.JTextField nameField = new javax.swing.JTextField(userDefault, 15); 
                JPasswordField passwordField = new JPasswordField(passwordDefault, 15);  
                               
                loginInfo.setLayout(new GridBagLayout());  
                
                GridBagConstraints gridBagConstraints = new GridBagConstraints();  
                gridBagConstraints.gridx = 0;  
                gridBagConstraints.gridy = 0;  
                gridBagConstraints.anchor = GridBagConstraints.EAST;  
                gridBagConstraints.insets = new Insets(0, 0, 5, 11);  
                loginInfo.add(serverLabel, gridBagConstraints);  

                gridBagConstraints = new GridBagConstraints();  
                gridBagConstraints.gridx = 1;  
                gridBagConstraints.gridy = 0;  
                gridBagConstraints.gridwidth = 1;  
                gridBagConstraints.anchor = GridBagConstraints.WEST;  
                gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;  
                gridBagConstraints.weightx = 1.0;  
                gridBagConstraints.insets = new Insets(0, 0, 5, 0);  
                loginInfo.add(serverName, gridBagConstraints);  
                
                gridBagConstraints = new GridBagConstraints();  
                gridBagConstraints.gridx = 0;  
                gridBagConstraints.gridy = 1;  
                gridBagConstraints.anchor = GridBagConstraints.EAST;  
                gridBagConstraints.insets = new Insets(0, 0, 5, 11);  
                loginInfo.add(nameLabel, gridBagConstraints);  

                gridBagConstraints = new GridBagConstraints();  
                gridBagConstraints.gridx = 1;  
                gridBagConstraints.gridy = 1;  
                gridBagConstraints.gridwidth = 1;  
                gridBagConstraints.anchor = GridBagConstraints.WEST;  
                gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;  
                gridBagConstraints.weightx = 1.0;  
                gridBagConstraints.insets = new Insets(0, 0, 5, 0);  
                loginInfo.add(nameField, gridBagConstraints);  

                gridBagConstraints = new GridBagConstraints();  
                gridBagConstraints.gridx = 0;  
                gridBagConstraints.gridy = 2;  
                gridBagConstraints.anchor = GridBagConstraints.EAST;  
                gridBagConstraints.insets = new Insets(0, 0, 11, 11);  
                loginInfo.add(passwordLabel, gridBagConstraints);  

                gridBagConstraints = new GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 2;  
                gridBagConstraints.gridwidth = 1;  
                gridBagConstraints.anchor = GridBagConstraints.WEST;  
                gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;  
                gridBagConstraints.weightx = 1.0;
                gridBagConstraints.insets = new Insets(0, 0, 11, 0);  
                loginInfo.add(passwordField, gridBagConstraints);  
                
                loginInfo.getComponent(5).setFont(loginInfo.getComponent(1).getFont());
                svc.setPanel(loginInfo);
                svc.addLoginListener(new MirthLoginListener());
                /*JXLoginPanel.Status status = JXLoginPanel.showLoginDialog(null, panel);
                if(status == JXLoginPanel.Status.SUCCEEDED)
                    new Mirth(svc.getClient());
                */
                
                final JXLoginPanel.JXLoginFrame frm = JXLoginPanel.showLoginFrame(panel);
                frm.setIconImage(new ImageIcon(com.webreach.mirth.client.ui.Frame.class.getResource("images/emoticon_smile.png")).getImage());
                
                frm.addWindowListener(new WindowAdapter()
                {
                    public void windowClosed(WindowEvent e)
                    {
                        JXLoginPanel.Status status = frm.getStatus();
                        if (status == JXLoginPanel.Status.SUCCEEDED)
                        {
                            new Mirth(svc.getClient());
                        } 
                        else
                        {
                            System.out.println("Login Failed: " + status);
                        }
                    }
                });

                frm.setVisible(true);
            }
        });
    }
}

class MirthLoginListener implements LoginListener 
{
        public MirthLoginListener() 
        {
        }
        
        public void loginSucceeded(LoginEvent source) 
        {
        }
        public void loginStarted(LoginEvent source) 
        {
        }
        public void loginFailed(LoginEvent source) 
        {
        }
        public void loginCanceled(LoginEvent source) 
        {
            System.exit(0);
        }
}

class MirthLoginService extends LoginService 
{
        Client c;
        JPanel p;
        
        public MirthLoginService() 
        {
        }
        
        public void setPanel(JPanel p)
        {
            this.p = p;
        }
        
        public Client getClient()
        {
            return c;
        }
        
        public boolean authenticate(final String username, char[] pass, String server) throws Exception 
        {
            String user = ((javax.swing.JTextField)p.getComponent(3)).getText();
            String pw =  ((javax.swing.JTextField)p.getComponent(5)).getText();
            String mirthServer = ((javax.swing.JTextField)p.getComponent(1)).getText();
            c = new Client(mirthServer);
            try
            {
                if(c.login(user,pw))
                {
                    PlatformUI.USER_NAME = user;
                    PlatformUI.SERVER_NAME = mirthServer;
                    return true;
                }
            }
            catch (ClientException ex)
            {
                System.out.println("Could not connect to server...");
            }      
            return false;
        }
}
