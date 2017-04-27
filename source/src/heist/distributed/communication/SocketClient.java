package heist.distributed.communication;

import java.io.*;
import java.net.*;

public class SocketClient
{
    public static void main(String argv[]) throws Exception
    {
        SocketClient client = new SocketClient("localhost", 6789);
        System.out.println(client.sendMessage("test_message"));
    }
    
    private final String address;
    private final int port;
    
    public SocketClient(String address, int port)
    {
        this.address = address;
        this.port = port;
    }
    
    public String sendMessage(String message) throws Exception
    {
        Socket socket = new Socket(this.address, this.port);
        
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.writeBytes(message);
        
        String received = in.readLine();
        System.out.println("Received " + received);
        
        socket.close();
        
        return received;
    }
}