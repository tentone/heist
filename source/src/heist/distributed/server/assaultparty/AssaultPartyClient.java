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
        
        this.sendMessage(message);
        
        //TODO <ADD CODE HERE>
        return 0;
    }

    @Override
    public int getTarget() throws Exception
    {
        //TODO <ADD CODE HERE>
        return 0;
    }

    @Override
    public int getState() throws Exception
    {
        //TODO <ADD CODE HERE>
        return 0;
    }

    @Override
    public boolean partyFull() throws Exception
    {
        //TODO <ADD CODE HERE>
        return false;
    }
    

    @Override
    public OrdinaryThief[] getThieves() throws Exception
    {
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
        //TODO <ADD CODE HERE>
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
        //TODO <ADD CODE HERE>
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
