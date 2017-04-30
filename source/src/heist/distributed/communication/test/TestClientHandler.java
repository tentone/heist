package heist.distributed.communication.test;

import heist.Configuration;
import heist.distributed.communication.ClientHandler;
import heist.distributed.communication.Message;
import heist.thief.OrdinaryThief;
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
        TestMessage response = new TestMessage(message.type + 1);
        response.thief = new OrdinaryThief(321, null, null, null, null, null, new Configuration());

        this.sendMessage(response);
    }
}