package heist.rmi.run.server;

import heist.concurrent.shared.SharedAssaultParty;
import heist.concurrent.shared.SharedConcentrationSite;
import heist.concurrent.shared.SharedControlCollectionSite;
import heist.concurrent.shared.SharedLogger;
import heist.concurrent.shared.SharedMuseum;
import heist.distributed.ConfigurationDistributed;
import heist.interfaces.*;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class LoggerRMI
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
            ControlCollectionSite controlCollection = (ControlCollectionSite) Naming.lookup(configuration.controlCollectionServer.rmiURL());
            
            Naming.rebind(configuration.loggerServer.rmiURL(), UnicastRemoteObject.exportObject(new SharedLogger(parties, museum, controlCollection, configuration), configuration.loggerServer.port));
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in RMI logger");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
