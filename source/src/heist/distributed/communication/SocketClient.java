package heist.distributed.communication;

import java.io.*;
import java.net.*;

public class SocketClient
{
    private final String address;
    private final int port;
    
    public SocketClient(String address, int port)
    {
        this.address = address;
        this.port = port;
    }
    
    public String sendMessage(String message) throws Exception
    {
        if(!message.endsWith("\n"))
        {
            message += "\n";
        }
        
        Socket socket = new Socket(this.address, this.port);
        
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.writeBytes(message);
        
        String received = in.readLine();
        
        socket.close();
        
        return received;
    }
}