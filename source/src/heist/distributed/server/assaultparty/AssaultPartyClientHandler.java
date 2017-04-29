package heist.distributed.server.assaultparty;

import heist.concurrent.shared.SharedAssaultParty;
import heist.distributed.communication.ClientHandler;
import java.io.IOException;
import java.net.Socket;

public class AssaultPartyClientHandler extends ClientHandler
{
    private SharedAssaultParty party;

    public AssaultPartyClientHandler(Socket socket, SharedAssaultParty party) throws IOException
    {
        super(socket);          

        this.party = party;
    }

    @Override
    public void run()
    {
        //TODO <ADD CODE HERE> 
    }
}