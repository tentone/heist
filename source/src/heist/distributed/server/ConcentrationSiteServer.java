package heist.distributed.server;

import heist.distributed.communication.ClientHandler;
import heist.distributed.communication.SocketServer;
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
    public void onClientConnection(Socket socket) throws IOException
    {
        new ConcentrationSiteClientHandler(socket, this.concentration).start();
    }
    
    class ConcentrationSiteClientHandler extends ClientHandler
    {
        private SharedConcentrationSite concentration;
        
        public ConcentrationSiteClientHandler(Socket socket, SharedConcentrationSite concentration) throws IOException
        {
            super(socket);
            
            this.concentration = concentration;
        }
        
        @Override
        public void run()
        {
            //TODO <ADD CODE HERE> 
        }
    }
}
