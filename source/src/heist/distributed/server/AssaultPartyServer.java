package heist.distributed.server;

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
    
    class AssaultPartyClientHandler extends Thread
    {
        public AssaultPartyClientHandler(Socket socket)
        {
            
        }
        //TODO <ADD CODE HERE>
    }
}
