package heist.distributed.communication;

import java.io.Serializable;

public class Message implements Serializable
{
    public String type;
    public String data;
    
    public Message(String type, String data)
    {
        this.type = type;
        this.data = data;
    }
    
    
}
