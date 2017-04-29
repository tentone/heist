package heist.distributed.communication.test;

import heist.distributed.communication.ClientHandler;
import heist.distributed.communication.SocketServer;
import java.io.IOException;
import java.net.Socket;

public class TestServer extends SocketServer
{
    public static void main(String[] args) throws IOException
    {
        new TestServer().start();
    }
    
    public TestServer() throws IOException
    {
        super(8000);
    }

    @Override
    public void onClientConnection(Socket socket) throws IOException
    {
        new TestClientHandler(socket).start();
    }
}
