package heist.distributed.server.assaultparty;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.communication.Client;
import heist.distributed.communication.Message;
import heist.interfaces.AssaultParty;
import heist.room.RoomStatus;
import heist.thief.OrdinaryThief;

public class AssaultPartyClient extends Client implements AssaultParty
{
    public AssaultPartyClient(int id, ConfigurationDistributed configuration)
    {
        super(configuration.assaultPartiesServers[id]);
    }
    
    @Override
    public Message sendMessage(Message message) throws Exception
    {
        System.out.println("AssaultPartyClientMessage:" + message.type);
        
        return super.sendMessage(message);
    }
    
    @Override
    public int getID() throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.GET_ID);
        
        AssaultPartyMessage response = (AssaultPartyMessage) this.sendMessage(message);
        
        return response.id;
    }

    @Override
    public int getTarget() throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.GET_TARGET);
        
        AssaultPartyMessage response = (AssaultPartyMessage) this.sendMessage(message);
        
        return response.target;
    }

    @Override
    public int getState() throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.GET_STATE);
        
        AssaultPartyMessage response = (AssaultPartyMessage) this.sendMessage(message);
        
        return response.state;
    }

    @Override
    public boolean partyFull() throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.GET_TARGET);
        
        AssaultPartyMessage response = (AssaultPartyMessage) this.sendMessage(message);
        
        return response.partyFull;
    }
    

    @Override
    public OrdinaryThief[] getThieves() throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.GET_THIEVES);
        
        AssaultPartyMessage response = (AssaultPartyMessage) this.sendMessage(message);

        if(response.thieves != null)
        {
            System.out.println("GetThieves: " + response.thieves + " (" + response.thieves.length + ")");
        }
        else
        {
            System.out.println("GetThieves: " + response.thieves);
        }
        
        return response.thieves;
    }

    @Override
    public void prepareParty(RoomStatus room) throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.PREPARE_PARTY);
        message.room = room;
        
        this.sendMessage(message);
    }

    @Override
    public void addThief(OrdinaryThief thief) throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.ADD_THIEF);
        message.thief = thief;
        
        AssaultPartyMessage response = (AssaultPartyMessage) this.sendMessage(message);
        thief.copyState(response.thief);
    }

    @Override
    public void sendParty() throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.SEND_PARTY);
        this.sendMessage(message);
    }

    @Override
    public boolean crawlIn(OrdinaryThief thief) throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.CRAWL_IN);
        message.thief = thief;
        
        AssaultPartyMessage response = (AssaultPartyMessage) this.sendMessage(message);
        
        thief.copyState(response.thief);
        
        return response.keepCrawling;
    }

    @Override
    public void reverseDirection() throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.REVERSE_DIRECTION);
        this.sendMessage(message);
    }

    @Override
    public boolean crawlOut(OrdinaryThief thief) throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.CRAWL_OUT);
        message.thief = thief;
        
        AssaultPartyMessage response = (AssaultPartyMessage) this.sendMessage(message);
        
        thief.copyState(response.thief);
        
        return response.keepCrawling;
    }

    @Override
    public void removeThief(int id) throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.REMOVE_THIEF);
        message.id = id;
        this.sendMessage(message);
    }
}
