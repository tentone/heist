package heist.distributed.server.logger;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.communication.Client;
import heist.interfaces.Logger;

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
    public void log() throws Exception
    {
        LoggerMessage send = new LoggerMessage(LoggerMessage.LOG);
        this.sendMessage(send);
    }

    @Override
    public void end() throws Exception
    {
        LoggerMessage send = new LoggerMessage(LoggerMessage.END);
        this.sendMessage(send);
    }
}
