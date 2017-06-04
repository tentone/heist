package heist.rmi.run.server;

import heist.concurrent.shared.SharedMuseum;
import heist.rmi.ConfigurationRMI;
import static heist.utils.Address.rmiAddress;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class MuseumRMI
{
    public static void main(String[] args)
    {
        String address = "localhost";
                
        try
        {
            ConfigurationRMI configuration = ConfigurationRMI.readFromFile("configuration.txt");
            
            //Server
            String rmiURL = rmiAddress(address, configuration.rmiPort, configuration.museumServer.name);
            Remote stub = UnicastRemoteObject.exportObject(new SharedMuseum(configuration), configuration.museumServer.port);
            Naming.rebind(rmiURL, stub);
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in RMI museum");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
