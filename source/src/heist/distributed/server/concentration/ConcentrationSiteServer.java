package heist.distributed.server.concentration;

import heist.concurrent.shared.SharedConcentrationSite;
import heist.distributed.ConfigurationDistributed;
import heist.distributed.communication.Server;
import java.io.IOException;
import java.net.Socket;

public class ConcentrationSiteServer extends Server
{
    private SharedConcentrationSite concentration;
    
    public ConcentrationSiteServer(ConfigurationDistributed configuration) throws IOException
    {
        super(configuration.concentrationServer.port);
        
        //concentration = new SharedConcentrationSite(...);
    }

    @Override
    public void acceptConnection(Socket socket) throws IOException
    {
        new ConcentrationSiteClientHandler(socket, concentration).start();
    }
}
