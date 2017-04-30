package heist.distributed;

import heist.concurrent.shared.SharedAssaultParty;
import heist.concurrent.shared.SharedConcentrationSite;
import heist.concurrent.shared.SharedControlCollectionSite;
import heist.concurrent.shared.SharedLogger;
import heist.concurrent.shared.SharedMuseum;
import heist.distributed.server.museum.MuseumClient;
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
        
        //Logger
        SharedLogger logger = new SharedLogger(configuration);

        //Party
        AssaultParty[] parties = new AssaultParty[configuration.numberParties];
        for(int i = 0; i < parties.length; i++)
        {
            parties[i] = new SharedAssaultParty(i, configuration);
        }
        
        //Museum
        Museum museum = new MuseumClient(configuration);//new SharedMuseum(configuration);
        
        //Concetrantion
        ConcentrationSite concentration = new SharedConcentrationSite(configuration);
        
        //Control and collection
        ControlCollectionSite controlCollection = new SharedControlCollectionSite(parties, museum, configuration);
        
        //OrdinaryThieves
        OrdinaryThief[] thieves = new OrdinaryThief[configuration.numberThieves];
        for(int i = 0; i < thieves.length; i++)
        {
            thieves[i] = new OrdinaryThief(i, controlCollection, concentration, museum, logger, configuration);
        }
        
        //MasterThief
        MasterThief master = new MasterThief(controlCollection, concentration, logger, configuration);
        
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
