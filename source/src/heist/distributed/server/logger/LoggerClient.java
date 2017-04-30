package heist.distributed.server.logger;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.communication.Client;
import heist.distributed.communication.Message;
import heist.interfaces.Logger;
import heist.thief.MasterThief;
import heist.thief.OrdinaryThief;

public class LoggerClient extends Client implements Logger
{
    public LoggerClient(ConfigurationDistributed configuration)
    {
        super(configuration.loggerServer);
    }

    @Override
    public void debug(String message) throws Exception
    {
        LoggerMessage send = new LoggerMessage(LoggerMessage.DEBUG);
        send.debug = message;
        
        this.sendMessage(send);
    }

    @Override
    public void log(OrdinaryThief thief) throws Exception
    {
        LoggerMessage send = new LoggerMessage(LoggerMessage.LOG_ORDINARY_THIEF);
        send.thief = thief;
        
        this.sendMessage(send);
    }

    @Override
    public void log(MasterThief master) throws Exception
    {
        LoggerMessage send = new LoggerMessage(LoggerMessage.LOG_MASTER_THIEF);
        send.master = master;
        
        this.sendMessage(send);
    }
    
    @Override
    public void end() throws Exception
    {
        LoggerMessage send = new LoggerMessage(Message.END);
        this.sendMessage(send);
    }
}
