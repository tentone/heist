/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heist.tests;

import java.io.*;
import java.net.*;

class SocketServer
{
    public static void main(String[] args) throws Exception
    {
        String received, sent;
        ServerSocket serverSocket = new ServerSocket(6789);

        while(true)
        {
            Socket socket = serverSocket.accept();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            
            received = in.readLine();
            
            System.out.println("Received: " + received);
            sent = received.toUpperCase() + "\n";
            
            System.out.println("Sent:" + sent);
            out.writeBytes(sent);
        }
    }
}