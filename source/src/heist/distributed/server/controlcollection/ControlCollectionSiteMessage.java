package heist.distributed.server.controlcollection;

import heist.distributed.communication.Message;
import heist.room.RoomStatus;
import heist.thief.OrdinaryThief;

public class ControlCollectionSiteMessage extends Message
{
    private static final long serialVersionUID = 1222277744666L;
    
    public static final int AM_I_NEEDED = 15;
    
    public static final int APPRAISE_SITE = 16;
    
    public static final int PREPARE_NEW_PARTY = 17;
    
    public static final int GET_ROOM_TO_ATTACK = 18;
    
    public static final int TAKE_A_REST = 19;
    
    public static final int HAND_A_CANVAS = 20;
    
    public static final int COLLECT_CANVAS = 21;
    
    public static final int SUM_UP_RESULTS = 22;
    
    public static final int GET_PARTIES = 23;
    
    public static final int TOTAL_PAINTINGS_STOLEN = 24;
    
    public OrdinaryThief thief;
    public boolean amINeeded;
    public int state, partyID, paintings;
    public RoomStatus room;
    
    public ControlCollectionSiteMessage(int type)
    {
        super(type);
    }   
}
