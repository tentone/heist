package heist.distributed.server.museum;

import heist.distributed.communication.Message;
import heist.room.Room;
import java.io.Serializable;

public class MuseumMessage extends Message implements Serializable
{
    private static final long serialVersionUID = 748378325273847L;
    
    public static final int ROLL_A_CANVAS = 25;
    
    public static final int GET_ROOMS = 26;
    
    public Room[] rooms = null;
    public int roomID;
    public boolean gotCanvas;
    
    public MuseumMessage(int type)
    {
        super(type);
    }
    
}
