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
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.PARTY_FULL);
        AssaultPartyMessage response = (AssaultPartyMessage) this.sendMessage(message);
        return response.partyFull;
    }

    @Override
    public int[] getThieves() throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.GET_THIEVES);
        AssaultPartyMessage response = (AssaultPartyMessage) this.sendMessage(message);
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
    public boolean keepCrawling(OrdinaryThief thief) throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.KEEP_CRAWLING);
        message.thief = thief;
        
        AssaultPartyMessage response = (AssaultPartyMessage) this.sendMessage(message);
        thief.copyState(response.thief);
        
        return response.keepCrawling;
    }
    
    @Override
    public int crawlIn(OrdinaryThief thief) throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.CRAWL_IN);
        message.thief = thief;
        
        AssaultPartyMessage response = (AssaultPartyMessage) this.sendMessage(message);
        thief.copyState(response.thief);
        
        return response.position;
    }

    @Override
    public void reverseDirection(OrdinaryThief thief) throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.REVERSE_DIRECTION);
        message.thief = thief;
        
        this.sendMessage(message);
    }

    @Override
    public int crawlOut(OrdinaryThief thief) throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.CRAWL_OUT);
        message.thief = thief;
        
        AssaultPartyMessage response = (AssaultPartyMessage) this.sendMessage(message);
        thief.copyState(response.thief);
        
        return response.position;
    }

    @Override
    public void removeThief(int id) throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.REMOVE_THIEF);
        message.id = id;
        this.sendMessage(message);
    }
    
    @Override
    public void end() throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(Message.END);
        this.sendMessage(message);
    }
}
