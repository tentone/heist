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
        ControlCollectionSiteMessage message = new ControlCollectionSiteMessage(ControlCollectionSiteMessage.AM_I_NEEDED);
        message.thief = thief;
        
        ControlCollectionSiteMessage response = (ControlCollectionSiteMessage) this.sendMessage(message);
        thief.copyState(response.thief);
        
        return response.amINeeded;
    }

    @Override
    public int appraiseSit() throws Exception
    {
        ControlCollectionSiteMessage message = new ControlCollectionSiteMessage(ControlCollectionSiteMessage.APPRAISE_SITE);
        ControlCollectionSiteMessage response = (ControlCollectionSiteMessage) this.sendMessage(message);
        return response.state;
    }

    @Override
    public int prepareNewParty(RoomStatus room) throws Exception
    {
        ControlCollectionSiteMessage message = new ControlCollectionSiteMessage(ControlCollectionSiteMessage.PREPARE_NEW_PARTY);
        message.room = room;
        
        ControlCollectionSiteMessage response = (ControlCollectionSiteMessage) this.sendMessage(message);
        return response.partyID;
    }

    @Override
    public RoomStatus getRoomToAttack() throws Exception
    {
        ControlCollectionSiteMessage message = new ControlCollectionSiteMessage(ControlCollectionSiteMessage.GET_ROOM_TO_ATTACK);
        ControlCollectionSiteMessage response = (ControlCollectionSiteMessage) this.sendMessage(message);
        return response.room;
    }

    @Override
    public void takeARest() throws Exception
    {
        ControlCollectionSiteMessage message = new ControlCollectionSiteMessage(ControlCollectionSiteMessage.TAKE_A_REST);
        this.sendMessage(message);
    }

    @Override
    public void handACanvas(OrdinaryThief thief) throws Exception
    {
        ControlCollectionSiteMessage message = new ControlCollectionSiteMessage(ControlCollectionSiteMessage.HAND_A_CANVAS);
        message.thief = thief;
        
        ControlCollectionSiteMessage response = (ControlCollectionSiteMessage) this.sendMessage(message);
        thief.copyState(response.thief);
    }

    @Override
    public void collectCanvas() throws Exception
    {
        ControlCollectionSiteMessage message = new ControlCollectionSiteMessage(ControlCollectionSiteMessage.COLLECT_CANVAS);
        this.sendMessage(message);
    }

    @Override
    public void sumUpResults() throws Exception
    {
        ControlCollectionSiteMessage message = new ControlCollectionSiteMessage(ControlCollectionSiteMessage.SUM_UP_RESULTS);
        this.sendMessage(message);
    }

    @Override
    public int totalPaintingsStolen() throws Exception
    {
        ControlCollectionSiteMessage message = new ControlCollectionSiteMessage(ControlCollectionSiteMessage.TOTAL_PAINTINGS_STOLEN);
        ControlCollectionSiteMessage response = (ControlCollectionSiteMessage) this.sendMessage(message);
        return response.paintings;
    }
    
}
