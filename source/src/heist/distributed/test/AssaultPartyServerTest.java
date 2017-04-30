package heist.distributed.test;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.server.assaultparty.AssaultPartyServer;

public class AssaultPartyServerTest
{
    public static void main(String[] args) throws Exception
    {
        ConfigurationDistributed configuration = new ConfigurationDistributed();
        
        new AssaultPartyServer(0, configuration).start();
        
        new AssaultPartyServer(1, configuration).start();
    }
}
