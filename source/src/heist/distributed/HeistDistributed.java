package heist.distributed;

import heist.concurrent.shared.SharedConcentrationSite;
import heist.concurrent.shared.SharedControlCollectionSite;
import heist.concurrent.shared.SharedLogger;
import heist.distributed.server.assaultparty.AssaultPartyClient;
import heist.distributed.server.assaultparty.AssaultPartyServer;
import heist.distributed.server.museum.MuseumClient;
import heist.distributed.server.museum.MuseumServer;
import heist.interfaces.AssaultParty;
import heist.interfaces.ConcentrationSite;
import heist.interfaces.ControlCollectionSite;
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
        
        //Logger
        SharedLogger logger = new SharedLogger(configuration);

        //Party
        AssaultParty[] parties = new AssaultParty[configuration.numberParties];
        for(int i = 0; i < parties.length; i++)
        {
            //parties[i] = new SharedAssaultParty(i, configuration);
            parties[i] = new AssaultPartyClient(i, configuration);
        }
        
        //Museum
        Museum museum = new MuseumClient(configuration);
        //Museum museum = new SharedMuseum(configuration);
        
        //Concetrantion
        ConcentrationSite concentration = new SharedConcentrationSite(parties, configuration);
        
        //Control and collection
        ControlCollectionSite controlCollection = new SharedControlCollectionSite(parties, museum, configuration);
        
        //OrdinaryThieves
        OrdinaryThief[] thieves = new OrdinaryThief[configuration.numberThieves];
        for(int i = 0; i < thieves.length; i++)
        {
            thieves[i] = new OrdinaryThief(i, controlCollection, concentration, museum, parties, logger, configuration);
        }
        
        //MasterThief
        MasterThief master = new MasterThief(controlCollection, concentration, parties, logger, configuration);
        
        //Attach to logger
        logger.attachElements(thieves, master, parties, museum, controlCollection);
        
        //Start thieves
        for(int i = 0; i < thieves.length; i++)
        {
            thieves[i].start();
        }
        master.start();
    }
}
