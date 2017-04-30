package heist.distributed.server.concentration;

import heist.distributed.communication.ClientHandler;
import heist.distributed.communication.Message;
import heist.interfaces.ConcentrationSite;
import java.io.IOException;
import java.net.Socket;

public class ConcentrationSiteClientHandler extends ClientHandler
{
    private final ConcentrationSite concentration;

    public ConcentrationSiteClientHandler(Socket socket, ConcentrationSite concentration) throws IOException
    {
        super(socket);          

        this.concentration = concentration;
    }

    @Override
    public void processMessage(Message msg) throws Exception
    {
        ConcentrationSiteMessage message = (ConcentrationSiteMessage) msg;
        int type = message.type;
        
        if(type == ConcentrationSiteMessage.FILL_ASSAULT_PARTY)
        {
            //this.concentration.fillAssaultParty(party);
            
            //TODO <ADD CODE HERE>
        }
        else if(type == ConcentrationSiteMessage.PREPARE_EXCURSION)
        {
            //this.concentration.prepareExcursion(thief);
            
            //TODO <ADD CODE HERE>
        }
    }
}
