package heist.distributed.client;

import heist.distributed.ConfigurationDistributed;
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
        
        MasterThief thief = new MasterThief(controlCollection, concentration, logger, configuration);
        thief.start();
    }
}
