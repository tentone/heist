package heist.distributed.server;

import heist.concurrent.shared.SharedConcentrationSite;
import java.io.IOException;
import java.net.Socket;

public class ConcentrationSiteServer extends SocketServer
{
    private SharedConcentrationSite concentration;
    
    public ConcentrationSiteServer(int port) throws IOException
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
                new ConcentrationSiteClientHandler(this.serverSocket.accept()).start();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    class ConcentrationSiteClientHandler extends ClientHandler
    {
        public ConcentrationSiteClientHandler(Socket socket) throws IOException
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
