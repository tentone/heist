package heist.rmi.run.server;

import heist.concurrent.shared.SharedAssaultParty;
import heist.rmi.ConfigurationRMI;
import java.rmi.Naming;
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
        }
        int id = Integer.parseInt(args[0]);
        
        try
        {
            ConfigurationRMI configuration = ConfigurationRMI.readFromFile("configuration.txt");
            Naming.rebind(configuration.assaultPartiesServers[id].rmiURL(), UnicastRemoteObject.exportObject(new SharedAssaultParty(id, configuration), configuration.assaultPartiesServers[id].port));
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in RMI AssaultParty");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
