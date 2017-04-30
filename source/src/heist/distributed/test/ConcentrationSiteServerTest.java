package heist.distributed.test;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.server.assaultparty.AssaultPartyClient;
import heist.distributed.server.concentration.ConcentrationSiteServer;
import heist.interfaces.AssaultParty;

public class ConcentrationSiteServerTest
{
    public static void main(String[] args) throws Exception
    {
        ConfigurationDistributed configuration = new ConfigurationDistributed();

        AssaultParty[] parties = new AssaultParty[configuration.numberParties];
        for(int i = 0; i < parties.length; i++)
        {
            parties[i] = new AssaultPartyClient(i, configuration);
        }

        new ConcentrationSiteServer(parties, configuration).start();
    }
}