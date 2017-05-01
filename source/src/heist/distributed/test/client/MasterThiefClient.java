package heist.distributed.test.client;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.server.assaultparty.AssaultPartyClient;
import heist.distributed.server.concentration.ConcentrationSiteClient;
import heist.distributed.server.controlcollection.ControlCollectionSiteClient;
import heist.distributed.server.logger.LoggerClient;
import heist.distributed.server.museum.MuseumClient;
import heist.interfaces.AssaultParty;
import heist.interfaces.ConcentrationSite;
import heist.interfaces.ControlCollectionSite;
import heist.interfaces.Logger;
import heist.interfaces.Museum;
import heist.thief.MasterThief;

public class MasterThiefClient
{
    public static void main(String[] args)
    {
        ConfigurationDistributed configuration = ConfigurationDistributed.readFromFile();

        AssaultParty[] parties = new AssaultParty[configuration.numberParties];
        for(int i = 0; i < parties.length; i++)
        {
            parties[i] = new AssaultPartyClient(i, configuration);
        }
        
        Museum museum = new MuseumClient(configuration);
        ConcentrationSite concentration = new ConcentrationSiteClient(configuration);
        ControlCollectionSite controlCollection = new ControlCollectionSiteClient(configuration);
        
        Logger logger = new LoggerClient(configuration);
        
        new MasterThief(controlCollection, concentration, museum, parties, logger, configuration).start();
    }
}
