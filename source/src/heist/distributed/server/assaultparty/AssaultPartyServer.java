package heist.distributed.server.assaultparty;

import heist.distributed.communication.Server;
import heist.concurrent.shared.SharedAssaultParty;
import heist.distributed.ConfigurationDistributed;
import heist.interfaces.AssaultParty;
import java.io.IOException;
import java.net.Socket;

public class AssaultPartyServer extends Server
{
    private AssaultParty party;
    
    public AssaultPartyServer(int id, ConfigurationDistributed configuration) throws IOException
    {
        super(configuration.assaultPartiesServers[id].port);
        
        this.party = new SharedAssaultParty(id, configuration);
    }
    
    @Override
    public void acceptConnection(Socket socket) throws IOException
    {
        new AssaultPartyClientHandler(socket, this, this.party).start();
    }
}
