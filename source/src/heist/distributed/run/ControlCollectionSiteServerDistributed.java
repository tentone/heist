package heist.distributed.run;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.server.assaultparty.AssaultPartyClient;
import heist.distributed.server.controlcollection.ControlCollectionSiteServer;
import heist.distributed.server.museum.MuseumClient;
import heist.interfaces.AssaultParty;
import heist.interfaces.Museum;

public class ControlCollectionSiteServerDistributed
{
    public static void main(String[] args)
    {
        try
        {
            ConfigurationDistributed configuration = ConfigurationDistributed.readFromFile("configuration.txt");

            AssaultParty[] parties = new AssaultParty[configuration.numberParties];
            for(int i = 0; i < parties.length; i++)
            {
                parties[i] = new AssaultPartyClient(i, configuration);
            }

            Museum museum = new MuseumClient(configuration);      

            new ControlCollectionSiteServer(parties, museum, configuration).start();
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in ControlCollectionSiteServer");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
