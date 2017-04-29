package heist.distributed.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Client Handlers are created by servers to process Messages received from clients.
 * A client handler is executed in a separate thread.
 * @author Jose Manuel
 */
public abstract class ClientHandler extends Thread
{
    /**
     * Connection socket.
     */
    private final Socket socket;
    
    /**
     * ObjectInputStream used to receive messages.
     */
    private final ObjectInputStream in;
    
    /**
     * ObjectOutputStream used to send messages.
     */
    private final ObjectOutputStream out;
    
    /**
     * ClientHandler constructor, the server creates Handlers for every incoming client connection.
     * @param socket Communication socket.
     * @throws IOException 
     */
    public ClientHandler(Socket socket) throws IOException
    {
        this.socket = socket;

        OutputStream output = this.socket.getOutputStream();
        this.out = new ObjectOutputStream(output);

        InputStream input = this.socket.getInputStream();;
        this.in = new ObjectInputStream(input);
    }
    
    /**
     * This method should be implemented by ClientHandlers and is used to process received messages.
     * @param message Message received.
     * @throws Exception An exception can be thrown.
     */
    public abstract void processMessage(Message message) throws Exception;
    
    /**
     * Send message to the client.
     * @throws Exception An exception can be thrown.
     */
    public void sendMessage(Message message) throws Exception
    {
        this.out.writeObject(message);
    }
    
    /**
     * Client handler thread run.
     */
    @Override
    public void run()
    {
        try
        {
            this.processMessage(this.getMessage());
            
            this.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Get the received message.
     * @return Message object
     * @throws Exception An exception can be thrown.
     */
    private Message getMessage() throws Exception
    {
        Object object = this.in.readObject();

        if(object instanceof Message)
        {
            return (Message) object;
        }
        
        return null;
    }
    
    /**
     * Close this handler.
     * @throws IOException 
     */
    private void close() throws IOException
    {
        this.in.close();
        this.out.close();
        this.socket.close();
    }
}
