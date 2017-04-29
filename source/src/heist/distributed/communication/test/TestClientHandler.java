package heist.distributed.communication.test;

import heist.distributed.communication.ClientHandler;
import heist.distributed.communication.Message;
import java.io.IOException;
import java.net.Socket;

public class TestClientHandler extends ClientHandler
{
    public TestClientHandler(Socket socket) throws IOException
    {
        super(socket);
    }

    @Override
    public void processMessage(Message message) throws Exception
    {
        message.type++;
        message.data = message.data.toUpperCase();
        
        this.sendMessage(message);
    }
}