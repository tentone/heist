package heist.distributed.communication.test;

import heist.Configuration;
import heist.distributed.communication.Client;
import heist.thief.OrdinaryThief;

public class TestClient
{
    public static void main(String[] args) throws Exception
    {
        Client client = new Client("localhost", 6001);
        
        TestMessage sent = new TestMessage(1);
        sent.thief = new OrdinaryThief(123, null, null, null, null, null, new Configuration());
        System.out.println("Message sent\n" + sent.toString());
        
        TestMessage received = (TestMessage) client.sendMessage(sent);
        System.out.println("Message received\n" + received.toString() + " Thief:" + received.thief.getID());
    }
}
