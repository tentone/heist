package heist.distributed.server.assaultparty;

import heist.distributed.communication.Message;
import heist.room.RoomStatus;
import heist.thief.OrdinaryThief;
import java.io.Serializable;

public class AssaultPartyMessage extends Message implements Serializable
{
    private static final long serialVersionUID = 265273489490126L;

    public static final int GET_ID = 1;
    
    public static final int GET_TARGET = 2;
    
    public static final int GET_STATE = 3;
    
    public static final int PARTY_FULL = 4;
    
    public static final int GET_THIEVES = 5;
    
    public static final int PREPARE_PARTY = 6;
    
    public static final int ADD_THIEF = 7;
    
    public static final int SEND_PARTY = 8;
    
    public static final int CRAWL_IN = 9;
    
    public static final int REVERSE_DIRECTION = 10;
    
    public static final int CRAWL_OUT = 11;

    public static final int REMOVE_THIEF = 12;
    
    public int id, target, state;
    public boolean partyFull, keepCrawling;
    public RoomStatus room;
    public int[] thieves;
    public OrdinaryThief thief;
    
    /**
     * Simple constructor without any aditional data.
     * @param type Message type.
     */
    public AssaultPartyMessage(int type)
    {
        super(type);
    }
}
