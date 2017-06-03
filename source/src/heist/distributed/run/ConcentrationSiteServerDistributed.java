package heist.distributed.run;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.server.assaultparty.AssaultPartyClient;
import heist.distributed.server.concentration.ConcentrationSiteServer;
import heist.interfaces.AssaultParty;

public class ConcentrationSiteServerDistributed
{
    public static void main(String[] args) throws Exception
    {
        try
        {
            ConfigurationDistributed configuration = ConfigurationDistributed.readFromFile("configuration.txt");
            
            System.out.println("Info: Starting AssaultParty Clients");
            AssaultParty[] parties = new AssaultParty[configuration.numberParties];
            for(int i = 0; i < parties.length; i++)
            {
                parties[i] = new AssaultPartyClient(i, configuration);
            }
            
            System.out.println("Info: Starting ConcentrationSiteServer ");
            new ConcentrationSiteServer(parties, configuration).start();
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in ConcentrationSiteServer");
            e.printStackTrace();
            System.exit(1);
        }
    }
}