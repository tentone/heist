package heist.distributed;

import heist.distributed.server.assaultparty.AssaultPartyClient;
import heist.distributed.server.assaultparty.AssaultPartyServer;
import heist.distributed.server.concentration.ConcentrationSiteClient;
import heist.distributed.server.concentration.ConcentrationSiteServer;
import heist.distributed.server.controlcollection.ControlCollectionSiteClient;
import heist.distributed.server.controlcollection.ControlCollectionSiteServer;
import heist.distributed.server.logger.LoggerClient;
import heist.distributed.server.logger.LoggerServer;
import heist.distributed.server.museum.MuseumClient;
import heist.distributed.server.museum.MuseumServer;
import heist.interfaces.AssaultParty;
import heist.interfaces.ConcentrationSite;
import heist.interfaces.ControlCollectionSite;
import heist.interfaces.Logger;
import heist.interfaces.Museum;
import heist.thief.MasterThief;
import heist.thief.OrdinaryThief;

/**
 * Distributed version of the heist.
 * Thieves communicate using TCP sockets.
 * @author Jose Manuel
 */
public class HeistDistributed
{
    public static void main(String[] args) throws Exception
    {
        //Configuration
        ConfigurationDistributed configuration = new ConfigurationDistributed();

        //Clients
        Museum museum = new MuseumClient(configuration);
        ConcentrationSite concentration = new ConcentrationSiteClient(configuration);
        ControlCollectionSite controlCollection = new ControlCollectionSiteClient(configuration);
        Logger logger = new LoggerClient(configuration);
        AssaultParty[] parties = new AssaultParty[configuration.numberParties];
        for(int i = 0; i < parties.length; i++)
        {
            parties[i] = new AssaultPartyClient(i, configuration);
        }

        //Servers
        new MuseumServer(configuration).start();
        new ConcentrationSiteServer(parties, configuration).start();
        new ControlCollectionSiteServer(parties, museum, configuration).start();
        new LoggerServer(parties, museum, controlCollection, configuration).start();
        for(int i = 0; i < parties.length; i++)
        {
            new AssaultPartyServer(i, configuration).start();
        }

        //OrdinaryThieves
        for(int i = 0; i < configuration.numberThieves; i++)
        {
            new OrdinaryThief(i, controlCollection, concentration, museum, parties, logger, configuration).start();
        }
        
        //MasterThief
        new MasterThief(controlCollection, concentration, museum, parties, logger, configuration).start();
    }
}
