package heist.distributed.server.museum;

import heist.distributed.communication.Message;
import heist.room.Room;

public class MuseumMessage extends Message
{
    public static final int ROLL_A_CANVAS = 25;
    
    public static final int GET_ROOMS = 26;
    
    public Room[] rooms = null;
    
    public MuseumMessage(int type)
    {
        super(type);
    }
    
}
