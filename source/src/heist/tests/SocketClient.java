package heist.tests;

import java.io.*;
import java.net.*;

public class SocketClient
{
    public static void main(String argv[]) throws Exception
    {        
        BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
        Socket socket = new Socket("localhost", 6789);
        
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        String sentence = reader.readLine();
        
        out.writeBytes(sentence + "\n");
        
        String modifiedSentence = in.readLine();
        
        System.out.println("Received " + modifiedSentence);
        socket.close();
    }
    
    public String sendMessage(String message, int port)
    {
        return "";
    }
}