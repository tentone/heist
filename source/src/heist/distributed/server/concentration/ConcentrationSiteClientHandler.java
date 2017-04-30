package heist.distributed.server.concentration;

import heist.concurrent.shared.SharedConcentrationSite;
import heist.distributed.communication.ClientHandler;
import heist.distributed.communication.Message;
import java.io.IOException;
import java.net.Socket;

public class ConcentrationSiteClientHandler extends ClientHandler
{
    private final SharedConcentrationSite concentration;

    public ConcentrationSiteClientHandler(Socket socket, SharedConcentrationSite concentration) throws IOException
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
