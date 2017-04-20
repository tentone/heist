package heist.tests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler extends Thread
{
    String received, sent;
    Socket socket;
    
    public ClientHandler(Socket socket)
    {
        this.socket = socket;
    }
        
    @Override
    public void run()
    {     
        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            received = in.readLine();

            System.out.println("Received: " + received);
            sent = received.toUpperCase() + "\n";

            System.out.println("Sent:" + sent);
            out.writeBytes(sent);
        }
        catch(Exception ex)
        {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
