package heist.distributed.test;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.server.assaultparty.AssaultPartyServer;

public class AssaultPartyAServerTest
{
    public static void main(String[] args) throws Exception
    {
        ConfigurationDistributed configuration = ConfigurationDistributed.readFromFile();
        
        new AssaultPartyServer(0, configuration).start();
    }
}
