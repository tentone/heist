package heist.distributed.test.client;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.server.assaultparty.AssaultPartyClient;
import heist.distributed.server.concentration.ConcentrationSiteClient;
import heist.distributed.server.controlcollection.ControlCollectionSiteClient;
import heist.distributed.server.logger.LoggerClient;
import heist.interfaces.AssaultParty;
import heist.interfaces.ConcentrationSite;
import heist.interfaces.ControlCollectionSite;
import heist.interfaces.Logger;
import heist.thief.MasterThief;

public class MasterThiefClient
{
    public static void main(String[] args)
    {
        ConfigurationDistributed configuration = new ConfigurationDistributed();

        AssaultParty[] parties = new AssaultParty[configuration.numberParties];
        for(int i = 0; i < parties.length; i++)
        {
            parties[i] = new AssaultPartyClient(i, configuration);
        }

        ConcentrationSite concentration = new ConcentrationSiteClient(configuration);
        ControlCollectionSite controlCollection = new ControlCollectionSiteClient(configuration);
        
        Logger logger = new LoggerClient(configuration);
        
        MasterThief thief = new MasterThief(controlCollection, concentration, parties, logger, configuration);
        thief.start();
    }
}
