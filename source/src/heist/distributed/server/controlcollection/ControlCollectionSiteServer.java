package heist.distributed.server.controlcollection;

import heist.concurrent.shared.SharedControlCollectionSite;
import heist.distributed.ConfigurationDistributed;
import heist.distributed.communication.Server;
import heist.interfaces.AssaultParty;
import heist.interfaces.ControlCollectionSite;
import heist.interfaces.Museum;
import java.io.IOException;
import java.net.Socket;

public class ControlCollectionSiteServer extends Server
{
    private ControlCollectionSite controlCollection;
    
    public ControlCollectionSiteServer(AssaultParty[] parties, Museum museum, ConfigurationDistributed configuration) throws Exception
    {
        super(configuration.controlCollectionServer.port);
        
        controlCollection = new SharedControlCollectionSite(parties, museum, configuration);
    }

    @Override
    public void acceptConnection(Socket socket) throws IOException
    {
        new ControlCollectionSiteClientHandler(socket, this, this.controlCollection).start();
    }
}