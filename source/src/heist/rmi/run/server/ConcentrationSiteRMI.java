package heist.rmi.run.server;

import heist.concurrent.shared.SharedConcentrationSite;
import heist.rmi.ConfigurationRMI;
import heist.interfaces.*;
import static heist.utils.Address.rmiAddress;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class ConcentrationSiteRMI
{
    public static void main(String[] args)
    {
        String address = "localhost";
        
        try
        {
            ConfigurationRMI configuration = ConfigurationRMI.readFromFile("configuration.txt");

            //Clients
            AssaultParty[] parties = new AssaultParty[2];
            for(int i = 0; i < parties.length; i++)
            {
                parties[i] = (AssaultParty) Naming.lookup(configuration.assaultPartiesServers[i].rmiURL(configuration.rmiPort));
            }
         
            //Server
            String rmiURL = rmiAddress(address, configuration.rmiPort, configuration.concentrationServer.name);
            Remote stub = UnicastRemoteObject.exportObject(new SharedConcentrationSite(parties, configuration), configuration.concentrationServer.port);
            Naming.rebind(rmiURL, stub);
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in ConcentrationSite RMI");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
