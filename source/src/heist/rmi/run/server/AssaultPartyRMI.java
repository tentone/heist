package heist.rmi.run.server;

import heist.concurrent.shared.SharedAssaultParty;
import heist.rmi.ConfigurationRMI;
import static heist.utils.Address.rmiAddress;
import java.rmi.Naming;
import java.rmi.Remote;
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
        String address = "localhost";
        
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
            System.out.println("Error: Error in RMI AssaultParty");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
