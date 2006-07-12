package com.webreach.mirth.client.ui;
import com.webreach.mirth.client.core.ClientException;
import java.util.prefs.Preferences;

/**
 * The status updater class has a thread that updates the status
 * panel every specified interval if the status panel is being viewed.
 */
public class StatusUpdater implements Runnable
{
    private final int DEFAULT_INTERVAL_TIME = 20;
    private static Preferences userPreferences; 
    Frame parent;
    int refreshRate;
    
    public StatusUpdater()
    {
        this.parent = PlatformUI.MIRTH_FRAME;
        userPreferences = Preferences.systemNodeForPackage(Mirth.class);
    }
    
    public void run()
    {
        try
        {
            while(!Thread.interrupted())
            {
                refreshRate = userPreferences.getInt("intervalTime", DEFAULT_INTERVAL_TIME) * 1000;
                Thread.sleep(refreshRate);
                if(parent.contentPane.getViewport().getComponents().length > 0 && parent.contentPane.getViewport().getComponent(0) == parent.statusListPage)
                {
                    parent.doRefresh();
                }
            }
        }
        catch(InterruptedException e)
        {
            // should happen when closed.
        }
    }
}
