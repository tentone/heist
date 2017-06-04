package heist.rmi.run.server;

import heist.concurrent.shared.SharedConcentrationSite;
import heist.distributed.ConfigurationDistributed;
import heist.interfaces.*;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class ConcentrationSiteRMI
{
    public static void main(String[] args)
    {
        try
        {
            ConfigurationDistributed configuration = ConfigurationDistributed.readFromFile("configuration.txt");

            AssaultParty[] parties = new AssaultParty[2];
            parties[0] = (AssaultParty) Naming.lookup(configuration.assaultPartiesServers[0].rmiURL());
            parties[1] = (AssaultParty) Naming.lookup(configuration.assaultPartiesServers[1].rmiURL());

            Naming.rebind(configuration.concentrationServer.rmiURL(), UnicastRemoteObject.exportObject(new SharedConcentrationSite(parties, configuration), configuration.concentrationServer.port));
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in ConcentrationSite RMI");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
