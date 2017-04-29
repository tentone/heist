package heist.distributed.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Server extends Thread
{
    public ServerSocket serverSocket;
    public int port;
    
    public Server(int port) throws IOException
    {
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
    }

    public abstract void acceptConnection(Socket socket) throws IOException;
    
    @Override
    public void run()
    {
        try
        {
            while(true)
            {
                this.acceptConnection(this.serverSocket.accept());
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
