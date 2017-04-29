package heist.distributed.communication;

import heist.utils.Address;
import java.io.*;
import java.net.*;

/**
 * Client class is by client instance to exchange messages
 * @author Jose Manuel
 */
public class Client
{
    /**
     * Server address.
     */
    private final String address;
    
    /**
     * Communication port.
     */
    private final int port;
    
    /**
     * Client constructor.
     * @param address Server address.
     * @param port Communication port.
     */
    public Client(String address, int port)
    {
        this.address = address;
        this.port = port;
    }
    
    /**
     * Client constructor.
     * @param address Server address.
     */
    public Client(Address address)
    {
        this.address = address.address;
        this.port = address.port;   
    }
    
    /**
     * Send message to server and wait for the answer.
     * @param message Message to send.
     * @return Server answer Message.
     * @throws Exception A exception can be throw during server connection or Message casting.
     */
    public Message sendMessage(Message message) throws Exception
    {
        Socket socket = new Socket(this.address, this.port);
        
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        
        out.writeObject(message);
        
        Object received = in.readObject();
        socket.close();
        
        if(received instanceof Message)
        {
            return (Message) received;
        }
        
        return null;
    }
}