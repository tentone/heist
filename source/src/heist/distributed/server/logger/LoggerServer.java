package heist.distributed.server.logger;

import heist.concurrent.shared.SharedLogger;
import heist.distributed.ConfigurationDistributed;
import heist.distributed.communication.Server;
import heist.interfaces.AssaultParty;
import heist.interfaces.ControlCollectionSite;
import heist.interfaces.Logger;
import heist.interfaces.Museum;
import java.io.IOException;
import java.net.Socket;

public class LoggerServer extends Server
{
    private Logger logger;
    
    public LoggerServer(AssaultParty[] parties, Museum museum, ControlCollectionSite controlCollection, ConfigurationDistributed configuration) throws IOException
    {
        super(configuration.loggerServer.port);
        
        logger = new SharedLogger(parties, museum, controlCollection, configuration);
    }

    @Override
    public void acceptConnection(Socket socket) throws IOException
    {
        new LoggerClientHandler(socket, this, this.logger).start();
    }
    
}
