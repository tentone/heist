package heist.distributed.server.assaultparty;

import heist.Configuration;
import heist.distributed.communication.Server;
import heist.concurrent.shared.SharedAssaultParty;
import java.io.IOException;
import java.net.Socket;

public class AssaultPartyServer extends Server
{
    public static void main(String[] args) throws IOException
    {
        new AssaultPartyServer(0, null).start();
    }
    
    private SharedAssaultParty party;
    
    public AssaultPartyServer(int id, Configuration configuration) throws IOException
    {
        super(10000);
        
        //this.party = new SharedAssaultParty();
    }
    
    @Override
    public void acceptConnection(Socket socket) throws IOException
    {
        new AssaultPartyClientHandler(socket, this.party).start();
    }
}
