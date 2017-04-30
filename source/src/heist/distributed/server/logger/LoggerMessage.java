package heist.distributed.server.logger;

import heist.distributed.communication.Message;
import java.io.Serializable;

public class LoggerMessage extends Message implements Serializable
{
    private static final long serialVersionUID = 2345387572843L;
    
    public static final int DEBUG = 27;
    
    public static final int LOG = 28;
    
    public static final int END = 29;
    
    public String debug = null;
    
    public LoggerMessage(int type)
    {
        super(type);
    }
    
    public LoggerMessage(int type, String debug)
    {
        super(type);
        
        this.debug = debug;
    }
}
