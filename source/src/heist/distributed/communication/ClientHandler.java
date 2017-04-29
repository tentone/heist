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
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    
    public ClientHandler(Socket socket) throws IOException
    {
        this.socket = socket;

        OutputStream output = this.socket.getOutputStream();
        this.out = new ObjectOutputStream(output);

        InputStream input = this.socket.getInputStream();;
        this.in = new ObjectInputStream(input);
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
        Object object = this.in.readObject();

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
