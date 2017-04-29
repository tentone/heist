package heist.distributed.communication;

import java.io.IOException;
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
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    
    public ClientHandler(Socket socket) throws IOException
    {
        this.socket = socket;
        
        System.out.println("Creating object input stream");
        this.in = new ObjectInputStream(this.socket.getInputStream());
        
        System.out.println("Creating output stream");
        OutputStream output = this.socket.getOutputStream();
        
        System.out.println("Creating object output stream");
        this.out = new ObjectOutputStream(output);
    }
    
    public abstract void processMessage(Message message) throws Exception;
    
    @Override
    public void run()
    {
        try
        {
            this.processMessage(this.getMessage());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public Message getMessage() throws IOException, ClassNotFoundException
    {
        System.out.println("Getting message");
        
        Object object = this.in.readObject();
        
        System.out.println("Readed message");
        
        if(object instanceof Message)
        {
            return (Message) object;
        }
        
        return null;
    }
    
    public void sendMessage(Message message) throws IOException
    {
        this.out.writeObject(message);
    }
    
    public void close() throws IOException
    {
        this.in.close();
        this.out.close();
        this.socket.close();
    }
}
