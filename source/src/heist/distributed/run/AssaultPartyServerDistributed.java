package heist.distributed.run;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.server.assaultparty.AssaultPartyServer;

public class AssaultPartyServerDistributed
{
    public static void main(String[] args) throws Exception
    {
        ConfigurationDistributed configuration = ConfigurationDistributed.readFromFile("configuration.txt");
        
        int id = Integer.parseInt(args[0]);
        
        new AssaultPartyServer(id, configuration).start();
    }
}
