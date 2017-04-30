package heist.distributed.server.assaultparty;

import heist.concurrent.shared.SharedAssaultParty;
import heist.distributed.communication.ClientHandler;
import heist.distributed.communication.Message;
import heist.interfaces.AssaultParty;
import java.io.IOException;
import java.net.Socket;

public class AssaultPartyClientHandler extends ClientHandler
{
    private final AssaultParty party;

    public AssaultPartyClientHandler(Socket socket, AssaultParty party) throws IOException
    {
        super(socket);          

        this.party = party;
    }

    @Override
    public void processMessage(Message msg) throws Exception
    {
        AssaultPartyMessage message = (AssaultPartyMessage) msg;
        AssaultPartyMessage response = new AssaultPartyMessage(Message.DEFAULT);

        int type = message.type;

        //System.out.println("Party Server " + this.party.getID() + ": " + type);
        
        if(type == AssaultPartyMessage.GET_ID)
        {
            response.id = this.party.getID();
        }
        else if(type == AssaultPartyMessage.GET_TARGET)
        {
            response.target = this.party.getTarget();
        }
        else if(type == AssaultPartyMessage.GET_STATE)
        {
            response.state = this.party.getState();
        }
        else if(type == AssaultPartyMessage.PARTY_FULL)
        {
            response.partyFull = this.party.partyFull();
        }
        else if(type == AssaultPartyMessage.GET_THIEVES)
        {
            response.thieves = this.party.getThieves();
        }
        else if(type == AssaultPartyMessage.PREPARE_PARTY)
        {
            this.party.prepareParty(message.room);
        }
        else if(type == AssaultPartyMessage.ADD_THIEF)
        {
            this.party.addThief(message.thief);
            
            response.thief = message.thief;
        }
        else if(type == AssaultPartyMessage.SEND_PARTY)
        {
            this.party.sendParty();
        }
        else if(type == AssaultPartyMessage.CRAWL_IN)
        {
            response.keepCrawling = this.party.crawlIn(message.thief);
            response.thief = message.thief;
        }
        else if(type == AssaultPartyMessage.REVERSE_DIRECTION)
        {
            this.party.reverseDirection(message.thief);
        }
        else if(type == AssaultPartyMessage.CRAWL_OUT)
        {
            response.keepCrawling = this.party.crawlOut(message.thief);
            response.thief = message.thief;
        }
        else if(type == AssaultPartyMessage.REMOVE_THIEF)
        {
            this.party.removeThief(message.id);
        }
        
        //System.out.println("Party Server " + this.party.getID() + ": " + type + " ok");
        
        this.sendMessage(response);
    }
}