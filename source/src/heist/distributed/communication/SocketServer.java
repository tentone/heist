package heist.distributed.communication;

import java.io.IOException;
import java.net.ServerSocket;

public abstract class SocketServer extends Thread
{
    public ServerSocket serverSocket;
    public int port;
    
    public SocketServer(int port) throws IOException
    {
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
    }
}
