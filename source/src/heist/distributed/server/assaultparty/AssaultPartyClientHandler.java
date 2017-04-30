package heist.distributed.server.assaultparty;

import heist.concurrent.shared.SharedAssaultParty;
import heist.distributed.communication.ClientHandler;
import heist.distributed.communication.Message;
import java.io.IOException;
import java.net.Socket;

public class AssaultPartyClientHandler extends ClientHandler
{
    private final SharedAssaultParty party;

    public AssaultPartyClientHandler(Socket socket, SharedAssaultParty party) throws IOException
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
            //OrdinaryThiefState thieves = this.party.getThieves();
            //TODO <ADD CODE HERE>
        }
        else if(type == AssaultPartyMessage.PREPARE_PARTY)
        {
            
            //RoomStatus room = message.room;
            //this.party.prepareParty(room);
        }
        else if(type == AssaultPartyMessage.ADD_THIEF)
        {
            //OrdinaryThiefState thief = message.thief;
            //this.party.addThief(thief);
            
             //TODO <ADD CODE HERE>
            //this.sendMessage(...)
        }
        else if(type == AssaultPartyMessage.SEND_PARTY)
        {
            this.party.sendParty();
        }
        else if(type == AssaultPartyMessage.CRAWL_IN)
        {
            //OrdinaryThiefState thief = message.thief;
            //this.party.crawlIn(thief);
            
            //TODO <ADD CODE HERE>
        }
        else if(type == AssaultPartyMessage.REVERSE_DIRECTION)
        {
            this.party.reverseDirection();
        }
        else if(type == AssaultPartyMessage.CRAWL_OUT)
        {
            //OrdinaryThiefState thief = message.thief;
            //this.party.crawlOut(thief);
            
            //TODO <ADD CODE HERE>
        }
        else if(type == AssaultPartyMessage.REMOVE_THIEF)
        {
            //int id = message.id;
            //this.party.removeThief(id);
            
            //TODO <ADD CODE HERE>
        }
        
        this.sendMessage(response);
    }
}