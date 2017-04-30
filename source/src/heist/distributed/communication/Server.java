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
    protected int port;
    
    /**
     * While true the server continues running.
     * If false the server stops accepting incoming connections. 
     */
    protected boolean alive;
    
    /**
     * Server constructor
     * @param port Port used for comunication.
     * @throws IOException An exception may be thrown.
     */
    public Server(int port) throws IOException
    {
        this.port = port;
        this.alive = true;
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
     * This method is used to shutdown the server instance.
     */
    public void shutdown() throws IOException
    {
        this.alive = false;
        this.serverSocket.close();
    }
    
    /**
     * Thread run method implements the server life cycle.
     */
    @Override
    public void run()
    {
        try
        {
            while(this.alive)
            {
                this.acceptConnection(this.serverSocket.accept());
            }
        }
        catch(Exception e)
        {
            if(this.alive)
            {
                e.printStackTrace();
            }
        }
        
        System.out.println("Server " + this.port + " terminated!");
    }
}
