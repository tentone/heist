/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heist.tests;

import java.net.*;

class SocketServer
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(6789);

        while(true)
        {
            new ClientHandler(serverSocket.accept()).start();
        }
    }
}
