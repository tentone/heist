package heist.distributed.communication.test;

import heist.distributed.communication.Server;
import java.io.IOException;
import java.net.Socket;

public class TestServer extends Server
{
    public static void main(String[] args) throws IOException
    {
        new TestServer().start();
    }
    
    public TestServer() throws IOException
    {
        super(6001);
        
        System.out.println("Started server");
    }

    @Override
    public void acceptConnection(Socket socket) throws IOException
    {
        System.out.println("Incoming connection");

        new TestClientHandler(socket).start();
        
        System.out.println("Accepted connection");
    }
}
