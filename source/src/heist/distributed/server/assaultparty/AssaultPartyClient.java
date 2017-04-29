package heist.distributed.server.assaultparty;

import heist.distributed.communication.Client;
import heist.interfaces.AssaultParty;
import heist.room.RoomStatus;
import heist.thief.OrdinaryThief;
import heist.utils.Address;

public class AssaultPartyClient extends Client implements AssaultParty
{
    public AssaultPartyClient(Address address)
    {
        super(address);
    }
    
    @Override
    public int getID()
    {
        
        return 0;
    }

    @Override
    public int getTarget()
    {
        return 0;
    }

    @Override
    public int getState()
    {
        return 0;
    }

    @Override
    public boolean partyFull()
    {
        return false;
    }
    

    @Override
    public OrdinaryThief[] getThieves()
    {
        return null;
    }

    @Override
    public void prepareParty(RoomStatus room)
    {
    }

    @Override
    public void addThief(OrdinaryThief thief) throws Exception
    {
    }

    @Override
    public void sendParty() throws Exception
    {
        
    }

    @Override
    public boolean crawlIn(OrdinaryThief thief) throws Exception
    {
        return false;
    }

    @Override
    public void reverseDirection() throws Exception
    {
        
    }

    @Override
    public boolean crawlOut(OrdinaryThief thief) throws Exception
    {
        return false;
    }

    @Override
    public void removeThief(int id) throws Exception
    {
        
    }
}
