package com.webreach.mirth.client.ui;
import com.webreach.mirth.client.core.ClientException;
import java.util.prefs.Preferences;

public class StatusUpdater implements Runnable
{
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
                refreshRate = userPreferences.getInt("intervalTime", 20) * 1000;
                Thread.sleep(refreshRate);
                if(parent.jScrollPane2.getViewport().getComponents().length > 0 && parent.jScrollPane2.getViewport().getComponent(0) == parent.statusListPage)
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
