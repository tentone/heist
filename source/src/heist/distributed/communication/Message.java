package heist.distributed.communication;

import java.io.Serializable;

/**
 * Messages are exchanged by clients and servers.
 * @author Jose Manuel
 */
public class Message implements Serializable
{
    private static final long serialVersionUID = 7526471155622776147L;
    
    public static int OK = 0;
    public static int ERROR = 1;

    public int status; 
    public int type;
    public String data;
    
    public Message(int type, String data)
    {
        this.status = Message.OK;
        this.type = type;
        this.data = data;
    }
    
    @Override
    public String toString()
    {
        return "Status:" + this.status + " Type:" + this.type + " Data:" + this.data;
    }
}
