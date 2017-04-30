package heist.distributed.server.logger;

import heist.distributed.communication.Message;
import heist.thief.MasterThief;
import heist.thief.OrdinaryThief;
import java.io.Serializable;

public class LoggerMessage extends Message implements Serializable
{
    private static final long serialVersionUID = 2345387572843L;
    
    public static final int DEBUG = 100;
    
    public static final int LOG_ORDINARY_THIEF = 27;
    
    public static final int LOG_MASTER_THIEF = 28;
    
    public static final int END = 29;
    
    public String debug;
    public MasterThief master;
    public OrdinaryThief thief;
    
    public LoggerMessage(int type)
    {
        super(type);
    }
}
