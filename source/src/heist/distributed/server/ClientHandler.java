package heist.distributed.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public abstract class ClientHandler extends Thread
{
    private Socket socket;
    private BufferedReader in;
    private DataOutputStream out;
    
    public ClientHandler(Socket socket) throws IOException
    {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.out = new DataOutputStream(this.socket.getOutputStream());
    }
    
    public String getMessage() throws IOException
    {
        return this.in.readLine();
    }
    
    public void sendMessage(String message) throws IOException
    {
        if(!message.endsWith("\n"))
        {
            message += "\n";
        }
        
        out.writeBytes(message);
    }
}
