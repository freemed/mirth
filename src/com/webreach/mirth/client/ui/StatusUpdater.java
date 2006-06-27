package com.webreach.mirth.client.ui;
import com.webreach.mirth.client.core.ClientException;

public class StatusUpdater implements Runnable
{
    Frame parent;
    int refreshRate;
    public StatusUpdater(Frame parent)
    {
        this.parent = parent;
    }
    
    public void run()
    {
        try
        {
            while(!Thread.interrupted())
            {
                refreshRate = 5000;
                Thread.sleep(refreshRate);
                if(parent.jScrollPane2.getViewport().getComponent(0) == parent.statusListPage)
                {
                    try
                    {
                        parent.status = parent.mirthClient.getChannelStatusList();
                        parent.statusListPage.makeStatusTable();
                    }
                    catch (ClientException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        }
        catch(InterruptedException e)
        {
        }
    }
}
