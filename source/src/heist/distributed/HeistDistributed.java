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

public class HeistDistributed
{
    public static void main(String[] args) throws Exception
    {
        //Configuration
        ConfigurationDistributed configuration = new ConfigurationDistributed();
        
        //Servers
        new AssaultPartyServer(0, configuration).start();
        new AssaultPartyServer(1, configuration).start();
        new MuseumServer(configuration).start();

        //Assault Parties clients
        AssaultParty[] parties = new AssaultParty[configuration.numberParties];
        for(int i = 0; i < parties.length; i++)
        {
            parties[i] = new AssaultPartyClient(i, configuration); //new SharedAssaultParty(i, configuration);
        }
        
        //ConcentrationSite server
        new ConcentrationSiteServer(parties, configuration).start();
        
        //Museum client
        Museum museum = new MuseumClient(configuration); //new SharedMuseum(configuration);

        //ControlCollectionSite server
        new ControlCollectionSiteServer(parties, museum, configuration).start();
        
        //Concetrantion client
        ConcentrationSite concentration = new ConcentrationSiteClient(configuration); //new SharedConcentrationSite(parties, configuration);
        
        //ControlCollectionSite client
        ControlCollectionSite controlCollection = new ControlCollectionSiteClient(configuration); //new SharedControlCollectionSite(parties, museum, configuration);
        
        //Logger
        Logger logger = new LoggerClient(configuration);//new SharedLogger(configuration);
        
        //OrdinaryThieves
        OrdinaryThief[] thieves = new OrdinaryThief[configuration.numberThieves];
        for(int i = 0; i < thieves.length; i++)
        {
            thieves[i] = new OrdinaryThief(i, controlCollection, concentration, museum, parties, logger, configuration);
        }
        
        //MasterThief
        MasterThief master = new MasterThief(controlCollection, concentration, parties, logger, configuration);
        
        //Attach elements to shared logger
        new LoggerServer(thieves, master, parties, museum, controlCollection, configuration).start();
        //logger.attachElements(thieves, master, parties, museum, controlCollection);
        
        //Start thieves
        for(int i = 0; i < thieves.length; i++)
        {
            thieves[i].start();
        }
        master.start();
    }
}
