package heist.distributed.configuration;

import heist.distributed.communication.ClientHandler;
import heist.distributed.communication.Message;
import heist.distributed.communication.Server;
import java.io.IOException;
import java.net.Socket;

public class ConfigurationClientHandler extends ClientHandler
{
    public ConfigurationClientHandler(Socket socket, Server server) throws IOException
    {
        super(socket, server);
    }

    @Override
    public void processMessage(Message message) throws Exception
    {
        
    }
}
