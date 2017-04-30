package heist.distributed.server.museum;

import heist.concurrent.shared.SharedMuseum;
import heist.distributed.ConfigurationDistributed;
import heist.distributed.communication.Server;
import heist.interfaces.Museum;
import java.io.IOException;
import java.net.Socket;

public class MuseumServer extends Server
{
    private Museum museum;
    
    public MuseumServer(ConfigurationDistributed configuration) throws IOException
    {
        super(configuration.museumServer.port);
        
        this.museum = new SharedMuseum(configuration);
    }

    @Override
    public void acceptConnection(Socket socket) throws IOException
    {
        new MuseumClientHandler(socket, this, this.museum).start();
    }
    
}
