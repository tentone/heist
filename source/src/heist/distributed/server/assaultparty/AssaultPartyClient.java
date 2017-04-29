package heist.distributed.server.assaultparty;

import heist.interfaces.AssaultParty;
import heist.room.RoomStatus;
import heist.thief.OrdinaryThief;

public class AssaultPartyClient implements AssaultParty
{
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

}
