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
            for(int i = 0; i < parties.length; i++)
            {
                parties[i] = (AssaultParty) Naming.lookup(configuration.assaultPartiesServers[i].rmiURL(configuration.rmiPort));
            }
            Museum museum = (Museum) Naming.lookup(configuration.museumServer.rmiURL(configuration.rmiPort));
            ConcentrationSite concentration = (ConcentrationSite) Naming.lookup(configuration.concentrationServer.rmiURL(configuration.rmiPort));
            ControlCollectionSite controlCollection = (ControlCollectionSite) Naming.lookup(configuration.controlCollectionServer.rmiURL(configuration.rmiPort));
            Logger logger = (Logger) Naming.lookup(configuration.loggerServer.rmiURL(configuration.rmiPort));

            for(int i = 0; i < configuration.numberThieves; i++)
            {
                new OrdinaryThief(i, controlCollection, concentration, museum, parties, logger, configuration).start();
            }
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in OrdinaryThieves (" + e + ")");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
