package heist.rmi.run.client;

import heist.rmi.ConfigurationRMI;
import heist.interfaces.*;
import heist.thief.*;
import static heist.utils.Address.rmiAddress;
import java.rmi.Naming;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class OrdinaryThievesRMI
{
    public static void main(String[] args)
    {
        try
        {
            String address = (args.length > 0) ?  args[0] : "localhost";
            int port = (args.length > 1) ?  Integer.parseInt(args[1]) : 22399;
            
            ConfigurationRMI configuration = ConfigurationRMI.readFromFile("configuration.txt");

            Museum museum = (Museum) Naming.lookup(rmiAddress(address, port, "museum"));
            AssaultParty[] parties = new AssaultParty[2];
            parties[0] = (AssaultParty) Naming.lookup(rmiAddress(address, port, "assaultParty0"));
            parties[1] = (AssaultParty) Naming.lookup(rmiAddress(address, port, "assaultParty1"));
            ConcentrationSite concentration = (ConcentrationSite) Naming.lookup(rmiAddress(address, port, "concentration"));
            ControlCollectionSite controlCollection = (ControlCollectionSite) Naming.lookup(rmiAddress(address, port, "controlCollection"));
            Logger logger = (Logger) Naming.lookup(rmiAddress(address, port, "logger"));
            
            for(int i = 0; i < configuration.numberThieves; i++)
            {
                new OrdinaryThief(i, controlCollection, concentration, museum, parties, logger, configuration).start();
                System.out.println("Info: OrdinaryThief " + i + " running");
            }
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in OrdinaryThieves (" + e + ")");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
