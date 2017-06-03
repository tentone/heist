package heist.rmi.run;

import heist.Configuration;
import heist.interfaces.*;
import heist.thief.*;
import static heist.utils.Address.rmiAddress;
import java.rmi.Naming;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class HeistClientsRMI
{
    public static void main(String[] args) throws Exception
    {
        Configuration configuration = new Configuration();

        Museum museum = (Museum) Naming.lookup(rmiAddress("localhost", 5000, "museum"));

        AssaultParty[] parties = new AssaultParty[2];
        parties[0] = (AssaultParty) Naming.lookup(rmiAddress("localhost", 5000, "assaultParty0"));
        parties[1] = (AssaultParty) Naming.lookup(rmiAddress("localhost", 5000, "assaultParty1"));

        ConcentrationSite concentration = (ConcentrationSite) Naming.lookup(rmiAddress("localhost", 5000, "concentration"));
        ControlCollectionSite controlCollection = (ControlCollectionSite) Naming.lookup(rmiAddress("localhost", 5000, "controlCollection"));
        Logger logger = (Logger) Naming.lookup(rmiAddress("localhost", 5000, "logger"));

        //OrdinaryThieves
        for(int i = 0; i < configuration.numberThieves; i++)
        {
            new OrdinaryThief(i, controlCollection, concentration, museum, parties, logger, configuration).start();
        }
        
        //MasterThief
        new MasterThief(controlCollection, concentration, museum, parties, logger, configuration).start();
    }
}
