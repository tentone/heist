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
public class AssaultPartiesRMI
{
    public static void main(String[] args)
    {
        String address = "localhost";
        
        try
        {
            ConfigurationRMI configuration = ConfigurationRMI.readFromFile("configuration.txt");
            
            //Server
            for(int id = 0; id < configuration.numberParties; id++)
            {
                String rmiURL = rmiAddress(address, configuration.rmiPort, configuration.assaultPartiesServers[id].name);
                Remote stub = UnicastRemoteObject.exportObject(new SharedAssaultParty(id, configuration), configuration.assaultPartiesServers[id].port);
                Naming.rebind(rmiURL, stub);
            }
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in RMI AssaultParty");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
