package heist.rmi.run.server;

import heist.concurrent.shared.SharedAssaultParty;
import heist.rmi.ConfigurationRMI;
import static heist.utils.Address.rmiAddress;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class AssaultPartyRMI
{
    public static void main(String[] args)
    {
        if(args.length < 1)
        {
            System.out.println("Error: Missing AssaultParty id as argument");
            System.exit(1);
        }
        
        int id = Integer.parseInt(args[0]);
        String address = (args.length > 1) ?  args[1] : "localhost";
        int port = (args.length > 2) ?  Integer.parseInt(args[2]) : 22399;
        boolean createRegistry = (args.length > 3) ?  Boolean.parseBoolean(args[3]) : false;
        
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
            
            //Server
            String rmiURL = rmiAddress(address, configuration.rmiPort, configuration.assaultPartiesServers[id].name);
            Remote stub = UnicastRemoteObject.exportObject(new SharedAssaultParty(id, configuration), configuration.assaultPartiesServers[id].port);
            Naming.rebind(rmiURL, stub);
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in RMI AssaultParty " + id + "(" + e + ")");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
