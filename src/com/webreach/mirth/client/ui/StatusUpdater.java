package com.webreach.mirth.client.ui;
import com.webreach.mirth.client.core.ClientException;

public class StatusUpdater implements Runnable
{
    Frame parent;
    int refreshRate;
    public StatusUpdater()
    {
        this.parent = PlatformUI.MIRTH_FRAME;
    }
    
    public void run()
    {
        try
        {
            while(!Thread.interrupted())
            {
                refreshRate = 5000;
                Thread.sleep(refreshRate);
                if(parent.jScrollPane2.getViewport().getComponents().length > 0 && parent.jScrollPane2.getViewport().getComponent(0) == parent.statusListPage)
                {
                    parent.doRefresh();
                }
            }
        }
        catch(InterruptedException e)
        {
        }
    }
}
