package heist.distributed.server;

import heist.concurrent.shared.SharedControlCollectionSite;

public class ControlCollectionSiteServer extends Thread
{
    private SharedControlCollectionSite controlCollection;
    
    @Override
    public void run()
    {
        this.controlCollection = null;
        
        
    }
    
    class ControlCollectionClientHandler extends Thread
    {
        //TODO <ADD CODE HERE>
    }
}
