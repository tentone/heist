package heist.rmi.run.server;

import heist.concurrent.shared.SharedConcentrationSite;
import heist.rmi.ConfigurationRMI;
import heist.interfaces.*;
import static heist.utils.Address.rmiAddress;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class ConcentrationSiteRMI
{
    public static void main(String[] args)
    {
       String address = (args.length > 0) ?  args[0] : "localhost";
        int port = (args.length > 1) ?  Integer.parseInt(args[1]) : 22399;
        boolean createRegistry = (args.length > 2) ?  Boolean.parseBoolean(args[2]) : false;
        
        System.setProperty("java.security.policy", "java.policy");
        System.setProperty("java.rmi.server.hostname", address);
        
        if(createRegistry)
        {
            try
            {
                LocateRegistry.createRegistry(port);
                
                String hostname = System.getProperty("java.rmi.server.hostname");
                System.out.println("Info: RMI registry started on " + hostname + ":" + port);
            }
            catch(Exception e)
            {
                System.out.println("Error: Failed to create RMI registry (" + e + ")");
                System.exit(1);
            }
        }
        
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
            
            System.out.println("Info: ConcentrationSite running");
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in ConcentrationSite RMI (" + e + ")");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
