package heist.distributed.communication.test;

import heist.distributed.communication.Client;
import heist.distributed.communication.Message;

public class TestClient
{
    public static void main(String[] args) throws Exception
    {
        Client client = new Client("localhost", 6001);
        
        Message sent = new Message(123, "cocoxixi");
        System.out.println("Message sent\n" + sent.toString());
        
        Message received = client.sendMessage(sent);
        System.out.println("Message received\n" + received.toString());
    }
}
