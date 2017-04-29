package heist.distributed.client;

import heist.Configuration;
import heist.interfaces.ConcentrationSite;
import heist.interfaces.ControlCollectionSite;
import heist.interfaces.Logger;
import heist.interfaces.Museum;
import heist.thief.OrdinaryThief;

public class OrdinaryThiefClient
{
    public static void main(String[] args)
    {
        Configuration configuration = null;
        Logger logger = null;
        Museum museum = null;
        ConcentrationSite concentration = null;
        ControlCollectionSite controlCollection = null;
        
        OrdinaryThief thief = new OrdinaryThief(0, controlCollection, concentration, museum, logger, configuration);
        thief.start();
    }
}
