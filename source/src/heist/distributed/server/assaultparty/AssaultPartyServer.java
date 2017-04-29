package heist.distributed.server.assaultparty;

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
        
        //this.party = new SharedAssaultParty(...);
    }
    
    @Override
    public void onClientConnection(Socket socket) throws IOException
    {
        new AssaultPartyClientHandler(socket, this.party).start();
    }
}
