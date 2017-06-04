package heist.rmi.run.server;

import heist.concurrent.shared.SharedMuseum;
import heist.rmi.ConfigurationRMI;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class MuseumRMI
{
    public static void main(String[] args)
    {
        try
        {
            ConfigurationRMI configuration = ConfigurationRMI.readFromFile("configuration.txt");
            Naming.rebind(configuration.museumServer.rmiURL(), UnicastRemoteObject.exportObject(new SharedMuseum(configuration), configuration.museumServer.port));
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in RMI museum");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
