package heist.distributed.server.controlcollection;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.communication.Client;
import heist.interfaces.ControlCollectionSite;
import heist.room.RoomStatus;
import heist.thief.OrdinaryThief;

public class ControlCollectionSiteClient extends Client implements ControlCollectionSite
{
    public ControlCollectionSiteClient(ConfigurationDistributed configuration)
    {
        super(configuration.controlCollectionServer);
    }

    @Override
    public boolean amINeeded(OrdinaryThief thief) throws Exception
    {
        return false;
    }

    @Override
    public int appraiseSit() throws Exception
    {
        return 0;
    }

    @Override
    public int prepareNewParty(RoomStatus room) throws Exception
    {
        return 0;
    }

    @Override
    public RoomStatus getRoomToAttack() throws Exception
    {
        return null;
    }

    @Override
    public void takeARest() throws Exception
    {
        
    }

    @Override
    public void handACanvas(OrdinaryThief thief) throws Exception
    {
        
    }

    @Override
    public void collectCanvas() throws Exception
    {
        
    }

    @Override
    public void sumUpResults() throws Exception
    {
        
    }

    @Override
    public int totalPaintingsStolen()
    {
        return -1;
    }
    
}
