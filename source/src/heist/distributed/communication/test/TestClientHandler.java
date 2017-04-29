package heist.distributed.communication.test;

import heist.distributed.communication.ClientHandler;
import java.io.IOException;
import java.net.Socket;

public class TestClientHandler extends ClientHandler
{
    public TestClientHandler(Socket socket) throws IOException
    {
        super(socket);
    }
}