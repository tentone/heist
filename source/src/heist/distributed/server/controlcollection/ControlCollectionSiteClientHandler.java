package heist.distributed.server.controlcollection;

import heist.distributed.communication.ClientHandler;
import heist.distributed.communication.Message;
import heist.distributed.communication.Server;
import heist.interfaces.ControlCollectionSite;
import java.io.IOException;
import java.net.Socket;

public class ControlCollectionSiteClientHandler extends ClientHandler
{
    private ControlCollectionSite controlCollection;
    
    public ControlCollectionSiteClientHandler(Socket socket, Server server, ControlCollectionSite controlCollection) throws IOException
    {
        super(socket, server);
        
        this.controlCollection = controlCollection;
    }

    @Override
    public void processMessage(Message msg) throws Exception
    {
        ControlCollectionSiteMessage message = (ControlCollectionSiteMessage) msg;
        ControlCollectionSiteMessage response = new ControlCollectionSiteMessage(Message.DEFAULT);
        
        int type = message.type;
        
        if(type == ControlCollectionSiteMessage.AM_I_NEEDED)
        {
            response.amINeeded = this.controlCollection.amINeeded(message.thief);
            response.thief = message.thief;
        }
        else if(type == ControlCollectionSiteMessage.APPRAISE_SITE)
        {
            response.state = this.controlCollection.appraiseSit();
        }
        else if(type == ControlCollectionSiteMessage.PREPARE_NEW_PARTY)
        {
            response.partyID = this.controlCollection.prepareNewParty(message.room);
        }
        else if(type == ControlCollectionSiteMessage.GET_ROOM_TO_ATTACK)
        {
            response.room = this.controlCollection.getRoomToAttack();
        }
        else if(type == ControlCollectionSiteMessage.TAKE_A_REST)
        {
            this.controlCollection.takeARest();
        }
        else if(type == ControlCollectionSiteMessage.HAND_A_CANVAS)
        {
            this.controlCollection.handACanvas(message.thief);
            response.thief = message.thief;
        }
        else if(type == ControlCollectionSiteMessage.COLLECT_CANVAS)
        {
            this.controlCollection.collectCanvas();
        }
        else if(type == ControlCollectionSiteMessage.SUM_UP_RESULTS)
        {
            this.controlCollection.sumUpResults();
        }
        else if(type == ControlCollectionSiteMessage.TOTAL_PAINTINGS_STOLEN)
        {
            response.paintings = this.controlCollection.totalPaintingsStolen();
        }
        else
        {
            response.status = Message.ERROR;
        }
        
        this.sendMessage(response);
    }
}
