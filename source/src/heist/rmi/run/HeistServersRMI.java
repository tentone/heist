package heist.rmi.run;

import heist.rmi.implementation.ControlCollectionSiteRMI;
import heist.rmi.implementation.AssaultPartyRMI;
import heist.rmi.implementation.ConcentrationSiteRMI;
import heist.rmi.implementation.MuseumRMI;
import heist.rmi.implementation.LoggerRMI;
import heist.Configuration;
import heist.interfaces.*;
import heist.thief.*;
import static heist.utils.Address.rmiAddress;
import java.rmi.Naming;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class HeistServersRMI
{
    public static void main(String[] args) throws Exception
    {
        Configuration configuration = new Configuration();

        Naming.rebind(rmiAddress("localhost", 5000, "museum"), new MuseumRMI(configuration));
        Naming.rebind(rmiAddress("localhost", 5000, "assaultParty0"), new AssaultPartyRMI(0, configuration));
        Naming.rebind(rmiAddress("localhost", 5000, "assaultParty1"), new AssaultPartyRMI(1, configuration));
        
        Museum museum = (Museum) Naming.lookup(rmiAddress("localhost", 5000, "museum"));

        AssaultParty[] parties = new AssaultParty[2];
        parties[0] = (AssaultParty) Naming.lookup(rmiAddress("localhost", 5000, "assaultParty0"));
        parties[1] = (AssaultParty) Naming.lookup(rmiAddress("localhost", 5000, "assaultParty1"));
        
        Naming.rebind(rmiAddress("localhost", 5000, "controlCollection"), new ControlCollectionSiteRMI(parties, museum, configuration));
        Naming.rebind(rmiAddress("localhost", 5000, "concentration"), new ConcentrationSiteRMI(parties, configuration));
       
        ControlCollectionSite controlCollection = (ControlCollectionSite) Naming.lookup(rmiAddress("localhost", 5000, "controlCollection"));
        Naming.rebind(rmiAddress("localhost", 5000, "logger"), new LoggerRMI(parties, museum, controlCollection, configuration));
    }
}
