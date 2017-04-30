package heist.distributed.server.logger;

import heist.concurrent.shared.SharedLogger;
import heist.distributed.ConfigurationDistributed;
import heist.distributed.communication.Server;
import java.io.IOException;
import java.net.Socket;

public class LoggerServer extends Server
{
    private SharedLogger logger;
    
    public LoggerServer(ConfigurationDistributed configuration) throws IOException
    {
        super(configuration.loggerServer.port);
        
        //logger = new SharedLogger(...);
    }

    @Override
    public void acceptConnection(Socket socket) throws IOException
    {
        new LoggerClientHandler(socket, this.logger).start();
    }
    
}
