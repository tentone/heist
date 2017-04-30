package heist.distributed.server.controlcollection;

import heist.concurrent.shared.SharedControlCollectionSite;
import heist.distributed.ConfigurationDistributed;
import heist.distributed.communication.Server;
import java.io.IOException;
import java.net.Socket;

public class ControlCollectionSiteServer extends Server
{
    private SharedControlCollectionSite controlCollection;
    
    public ControlCollectionSiteServer(ConfigurationDistributed configuration) throws IOException
    {
        super(configuration.controlCollectionServer.port);
        
        //controlCollection = new SharedControlCollectionSite(...);
    }

    @Override
    public void acceptConnection(Socket socket) throws IOException
    {
        new ControlCollectionSiteClientHandler(socket, this.controlCollection).start();
    }
}