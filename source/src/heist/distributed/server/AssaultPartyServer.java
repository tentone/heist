package heist.distributed.server;

import heist.distributed.communication.ClientHandler;
import heist.distributed.communication.SocketServer;
import heist.concurrent.shared.SharedAssaultParty;
import java.io.IOException;
import java.net.Socket;

public class AssaultPartyServer extends SocketServer
{
    private SharedAssaultParty party;
    
    public AssaultPartyServer(int port) throws IOException
    {
        super(port);
    }
    
    @Override
    public void run()
    {
        try
        {
            while(true)
            {
                new AssaultPartyClientHandler(this.serverSocket.accept()).start();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    class AssaultPartyClientHandler extends ClientHandler
    {
        public AssaultPartyClientHandler(Socket socket) throws IOException
        {
            super(socket);          
        }
        
        @Override
        public void run()
        {
            //TODO <ADD CODE HERE> 
        }
    }
}
