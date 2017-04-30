package heist.distributed.communication;

import java.io.Serializable;

/**
 * Messages are exchanged by clients and servers.
 * This class is used as base to implement other message structures.
 * @author Jose Manuel
 */
public class Message implements Serializable
{
    private static final long serialVersionUID = 7526471155622776147L;
    
    /**
     * Default message.
     * Used for response messages.
     */
    public final static int DEFAULT = 1000;
    
    /**
     * End message.
     * Used to request server shutdown.
     */
    public final static int END = 2000;
    
    /**
     * Message OK state.
     */
    public final static int OK = 0;
    
    /**
     * Message ERROR state.
     */
    public final static int ERROR = 1;

    /**
     * Message state.
     */
    public int status;
    
    /**
     * Message type.
     */
    public int type;
    
    /**
     * Message constructor.
     * @param type Message type.
     */
    public Message(int type)
    {
        this.status = Message.OK;
        this.type = type;
    }
    
    /**
     * Message information for debugging.
     * @return Message status and type as string.
     */
    @Override
    public String toString()
    {
        return "Status:" + this.status + " Type:" + this.type;
    }
}
