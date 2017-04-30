package heist.distributed.server.logger;

import heist.concurrent.shared.SharedLogger;
import heist.distributed.communication.ClientHandler;
import heist.distributed.communication.Message;
import java.io.IOException;
import java.net.Socket;

public class LoggerClientHandler extends ClientHandler
{
    private final SharedLogger logger;
    
    public LoggerClientHandler(Socket socket, SharedLogger logger) throws IOException
    {
        super(socket);
        
        this.logger = logger;
    }

    @Override
    public void processMessage(Message msg) throws Exception
    {
        LoggerMessage message = (LoggerMessage) msg;
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
    }
    
}
