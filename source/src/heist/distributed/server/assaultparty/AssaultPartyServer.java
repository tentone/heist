package heist.distributed.server.assaultparty;

import heist.distributed.communication.Server;
import heist.concurrent.shared.SharedAssaultParty;
import java.io.IOException;
import java.net.Socket;

public class AssaultPartyServer extends Server
{
    public static void main(String[] args) throws IOException
    {
        new AssaultPartyServer(23291).start();
    }
    
    private SharedAssaultParty party;
    
    public AssaultPartyServer(int port) throws IOException
    {
        super(port);
        
        //this.party = new SharedAssaultParty(...);
    }
    
    @Override
    public void acceptConnection(Socket socket) throws IOException
    {
        new AssaultPartyClientHandler(socket, this.party).start();
    }
}
