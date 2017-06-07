package heist.rmi.run.server;

import heist.concurrent.shared.SharedConcentrationSite;
import heist.rmi.ConfigurationRMI;
import heist.interfaces.*;
import heist.rmi.register.RegistryServiceInterface;
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

        if(createRegistry)
        {
            try
            {
                System.setProperty("java.rmi.server.hostname", address);
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
            //Register
            RegistryServiceInterface registry = (RegistryServiceInterface) Naming.lookup(rmiAddress(address, port, "registry"));

            //Clients
            AssaultParty[] parties = new AssaultParty[2];
            parties[0] = (AssaultParty) Naming.lookup(rmiAddress(address, port, "assaultParty0"));
            parties[1] = (AssaultParty) Naming.lookup(rmiAddress(address, port, "assaultParty1"));
         
            //Server
            String rmiURL = rmiAddress(address, configuration.rmiPort, configuration.concentrationServer.name);
            Remote stub = UnicastRemoteObject.exportObject(new SharedConcentrationSite(parties, configuration), configuration.concentrationServer.port);
            //Naming.rebind(rmiURL, stub);
            registry.rebind(configuration.concentrationServer.name, stub);
            
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
