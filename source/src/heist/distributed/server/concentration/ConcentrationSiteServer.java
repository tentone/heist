package heist.distributed.server.concentration;

import heist.concurrent.shared.SharedConcentrationSite;
import heist.distributed.ConfigurationDistributed;
import heist.distributed.communication.Server;
import heist.interfaces.AssaultParty;
import heist.interfaces.ConcentrationSite;
import java.io.IOException;
import java.net.Socket;

public class ConcentrationSiteServer extends Server
{
    private ConcentrationSite concentration;
    
    public ConcentrationSiteServer(AssaultParty[] parties, ConfigurationDistributed configuration) throws IOException
    {
        super(configuration.concentrationServer.port);
        
        this.concentration = new SharedConcentrationSite(parties, configuration);
    }

    @Override
    public void acceptConnection(Socket socket) throws IOException
    {
        new ConcentrationSiteClientHandler(socket, this.concentration).start();
    }
}
