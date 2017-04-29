package heist.distributed.communication;

import java.io.Serializable;

public class Message implements Serializable
{
    public int type;
    public String data;
    
    public Message(int type, String data)
    {
        this.type = type;
        this.data = data;
    }
    
    
}
