package heist.distributed.server.concentration;

import heist.distributed.communication.ClientHandler;
import heist.distributed.communication.Message;
import heist.distributed.communication.Server;
import heist.interfaces.ConcentrationSite;
import java.io.IOException;
import java.net.Socket;

public class ConcentrationSiteClientHandler extends ClientHandler
{
    private final ConcentrationSite concentration;

    public ConcentrationSiteClientHandler(Socket socket, Server server, ConcentrationSite concentration) throws IOException
    {
        super(socket, server);          

        this.concentration = concentration;
    }

    @Override
    public void processMessage(Message msg) throws Exception
    {
        ConcentrationSiteMessage message = (ConcentrationSiteMessage) msg;
        ConcentrationSiteMessage response = new ConcentrationSiteMessage(Message.DEFAULT);
        
        int type = message.type;
        
        if(type == ConcentrationSiteMessage.FILL_ASSAULT_PARTY)
        {
            this.concentration.fillAssaultParty(message.partyID);
        }
        else if(type == ConcentrationSiteMessage.PREPARE_EXCURSION)
        {
            response.partyID = this.concentration.prepareExcursion(message.thief);
            response.thief = message.thief;
        }
        else
        {
            response.status = Message.ERROR;
        }
        
        this.sendMessage(response);
    }
}
