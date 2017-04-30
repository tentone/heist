package heist.distributed.test.client;

import heist.distributed.ConfigurationDistributed;
import heist.interfaces.AssaultParty;
import heist.interfaces.ConcentrationSite;
import heist.interfaces.ControlCollectionSite;
import heist.interfaces.Logger;
import heist.thief.MasterThief;

public class MasterThiefClient
{
    public static void main(String[] args)
    {
        ConfigurationDistributed configuration = null;
        
        Logger logger = null;
        ConcentrationSite concentration = null;
        ControlCollectionSite controlCollection = null;
        AssaultParty[] parties = null;
        
        MasterThief thief = new MasterThief(controlCollection, concentration, parties, logger, configuration);
        thief.start();
    }
}
