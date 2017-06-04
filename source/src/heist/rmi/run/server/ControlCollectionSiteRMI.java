package heist.rmi.run.server;

import heist.concurrent.shared.SharedControlCollectionSite;
import heist.distributed.ConfigurationDistributed;
import heist.interfaces.*;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class ControlCollectionSiteRMI
{
    public static void main(String[] args)
    {
        try
        {
            ConfigurationDistributed configuration = ConfigurationDistributed.readFromFile("configuration.txt");
            
            Museum museum = (Museum) Naming.lookup(configuration.museumServer.rmiURL());
            
            AssaultParty[] parties = new AssaultParty[2];
            parties[0] = (AssaultParty) Naming.lookup(configuration.assaultPartiesServers[0].rmiURL());
            parties[1] = (AssaultParty) Naming.lookup(configuration.assaultPartiesServers[1].rmiURL());

            Naming.rebind(configuration.controlCollectionServer.rmiURL(), UnicastRemoteObject.exportObject(new SharedControlCollectionSite(parties, museum, configuration), configuration.controlCollectionServer.port));
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in ControlCollectionSite RMI");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
