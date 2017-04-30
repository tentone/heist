package heist.distributed.test;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.server.assaultparty.AssaultPartyServer;

public class AssaultPartyServerTest
{
    public static void main(String[] args) throws Exception
    {
        ConfigurationDistributed configuration = new ConfigurationDistributed();
        
        for(int i = 0; i < configuration.numberParties; i++)
        {
            new AssaultPartyServer(i, configuration).start();
        }
    }
}
