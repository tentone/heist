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
