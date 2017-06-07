package heist.rmi.run.server;

import heist.concurrent.shared.SharedAssaultParty;
import heist.rmi.ConfigurationRMI;
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
public class AssaultPartiesRMI
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

            //Server
            for(int id = 0; id < configuration.numberParties; id++)
            {
                String rmiURL = rmiAddress(address, port, configuration.assaultPartiesServers[id].name);
                Remote stub = UnicastRemoteObject.exportObject(new SharedAssaultParty(id, configuration), configuration.assaultPartiesServers[id].port);
 
                //Naming.rebind(rmiURL, stub);
                registry.rebind(configuration.assaultPartiesServers[id].name, stub);
            }
            
            System.out.println("Info: AssaultParties running");
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in RMI AssaultParty (" + e + ")");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
