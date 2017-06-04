package heist.rmi.run.client;

import heist.rmi.ConfigurationRMI;
import heist.interfaces.*;
import heist.thief.*;
import java.rmi.Naming;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class OrdinaryThievesRMI
{
    public static void main(String[] args)
    {
        try
        {
            ConfigurationRMI configuration = ConfigurationRMI.readFromFile("configuration.txt");

            AssaultParty[] parties = new AssaultParty[2];
            parties[0] = (AssaultParty) Naming.lookup(configuration.assaultPartiesServers[0].rmiURL());
            parties[1] = (AssaultParty) Naming.lookup(configuration.assaultPartiesServers[1].rmiURL());
            Museum museum = (Museum) Naming.lookup(configuration.museumServer.rmiURL());
            ConcentrationSite concentration = (ConcentrationSite) Naming.lookup(configuration.concentrationServer.rmiURL());
            ControlCollectionSite controlCollection = (ControlCollectionSite) Naming.lookup(configuration.controlCollectionServer.rmiURL());
            Logger logger = (Logger) Naming.lookup(configuration.loggerServer.rmiURL());

            for(int i = 0; i < configuration.numberThieves; i++)
            {
                new OrdinaryThief(i, controlCollection, concentration, museum, parties, logger, configuration).start();
            }
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in OrdinaryThieves");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
