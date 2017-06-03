package heist.distributed.run;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.server.assaultparty.AssaultPartyClient;
import heist.distributed.server.controlcollection.ControlCollectionSiteClient;
import heist.distributed.server.logger.LoggerServer;
import heist.distributed.server.museum.MuseumClient;
import heist.interfaces.AssaultParty;
import heist.interfaces.ControlCollectionSite;
import heist.interfaces.Museum;

public class LoggerServerDistributed
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
            ControlCollectionSite controlCollection = new ControlCollectionSiteClient(configuration);

            new LoggerServer(parties, museum, controlCollection, configuration).start();
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in LoggerServer");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
