package heist.distributed.communication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler extends Thread
{
    private String received, sent;
    private Socket socket;
    
    public ClientHandler(Socket socket)
    {
        this.socket = socket;
    }
        
    @Override
    public void run()
    {     
        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());

            this.received = in.readLine();

            System.out.println("Received: " + this.received);
            this.sent = received.toUpperCase() + "\n";

            System.out.println("Sent:" + this.sent);
            out.writeBytes(this.sent);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
