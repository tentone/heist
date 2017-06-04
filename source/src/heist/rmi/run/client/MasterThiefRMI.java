package heist.rmi.run.client;

import heist.distributed.ConfigurationDistributed;
import heist.interfaces.*;
import heist.thief.*;
import java.rmi.Naming;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class MasterThiefRMI
{
    public static void main(String[] args)
    {
        try
        {
            ConfigurationDistributed configuration = ConfigurationDistributed.readFromFile("configuration.txt");

            AssaultParty[] parties = new AssaultParty[2];
            parties[0] = (AssaultParty) Naming.lookup(configuration.assaultPartiesServers[0].rmiURL());
            parties[1] = (AssaultParty) Naming.lookup(configuration.assaultPartiesServers[1].rmiURL());
            Museum museum = (Museum) Naming.lookup(configuration.museumServer.rmiURL());
            ConcentrationSite concentration = (ConcentrationSite) Naming.lookup(configuration.concentrationServer.rmiURL());
            ControlCollectionSite controlCollection = (ControlCollectionSite) Naming.lookup(configuration.controlCollectionServer.rmiURL());
            Logger logger = (Logger) Naming.lookup(configuration.loggerServer.rmiURL());

            new MasterThief(controlCollection, concentration, museum, parties, logger, configuration).start();
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in MasterThief");
            e.printStackTrace();
            System.exit(1);
        }
    }
}