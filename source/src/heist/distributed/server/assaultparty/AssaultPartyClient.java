package heist.distributed.server.assaultparty;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.communication.Client;
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
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.GET_TARGET);
        
        AssaultPartyMessage response = (AssaultPartyMessage) this.sendMessage(message);
        
        return response.partyFull;
    }
    

    @Override
    public OrdinaryThief[] getThieves() throws Exception
    {
        AssaultPartyMessage message = new AssaultPartyMessage(AssaultPartyMessage.GET_THIEVES);
        
        AssaultPartyMessage response = (AssaultPartyMessage) this.sendMessage(message);
        
        //TODO <ADD CODE HERE>
         
        return null;
    }

    @Override
    public void prepareParty(RoomStatus room) throws Exception
    {
        //TODO <ADD CODE HERE>
    }

    @Override
    public void addThief(OrdinaryThief thief) throws Exception
    {
        //TODO <ADD CODE HERE>
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
        //TODO <ADD CODE HERE>
        return false;
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
        //TODO <ADD CODE HERE>
        return false;
    }

    @Override
    public void removeThief(int id) throws Exception
    {
        //TODO <ADD CODE HERE>
    }
}
