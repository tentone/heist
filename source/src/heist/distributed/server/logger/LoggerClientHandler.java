package heist.distributed.server.logger;

import heist.distributed.communication.ClientHandler;
import heist.distributed.communication.Message;
import heist.interfaces.Logger;
import java.io.IOException;
import java.net.Socket;

public class LoggerClientHandler extends ClientHandler
{
    private final Logger logger;
    
    public LoggerClientHandler(Socket socket, Logger logger) throws IOException
    {
        super(socket);
        
        this.logger = logger;
    }

    @Override
    public void processMessage(Message msg) throws Exception
    {
        LoggerMessage message = (LoggerMessage) msg;
        LoggerMessage response = new LoggerMessage(Message.DEFAULT);
        
        int type = message.type;
        
        if(type == LoggerMessage.DEBUG)
        {
            this.logger.debug(message.debug);
        }
        else if(type == LoggerMessage.LOG)
        {
            this.logger.log();
        }
        else if(type == LoggerMessage.END)
        {
            this.logger.end();
        }
        
        this.sendMessage(response);
    }
    
}
