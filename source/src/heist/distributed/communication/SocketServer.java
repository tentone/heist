package heist.distributed.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class SocketServer extends Thread
{
    public ServerSocket serverSocket;
    public int port;
    
    public SocketServer(int port) throws IOException
    {
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
    }
    
    public abstract void onClientConnection(Socket socket) throws IOException;
    
    @Override
    public void run()
    {
        try
        {
            while(true)
            {
                onClientConnection(this.serverSocket.accept());
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
