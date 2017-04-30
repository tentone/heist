package heist.distributed.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The server class should be used as base for every server implementation.
 * @author Jose Manuel
 */
public abstract class Server extends Thread
{
    /**
     * Server socket.
     */
    public ServerSocket serverSocket;
    
    /**
     * Server port.
     */
    public int port;
    
    /**
     * Server constructor
     * @param port Port used for comunication.
     * @throws IOException An exception may be thrown.
     */
    public Server(int port) throws IOException
    {
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
    }
    
    /**
     * This method is used to launch new ClientHandlers.
     * Should be implemented by all servers.
     * @param socket Socket used for this connection.
     * @throws IOException An exception may be thrown.
     */
    public abstract void acceptConnection(Socket socket) throws IOException;
    
    /**
     * Thread run method implements the server life cycle.
     */
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
