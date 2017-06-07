package heist.rmi.local;

import heist.interfaces.*;
import heist.rmi.ConfigurationRMI;
import heist.thief.*;
import static heist.utils.Address.rmiAddress;
import java.rmi.Naming;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class HeistClientsRMI
{
    public static void main(String[] args)
    {
        String address = (args.length > 0) ?  args[0] : "localhost";
        int port = (args.length > 1) ?  Integer.parseInt(args[1]) : 22398;

        try
        {
            ConfigurationRMI configuration = new ConfigurationRMI();
            
            Museum museum = (Museum) Naming.lookup(rmiAddress(address, port, "museum"));

            AssaultParty[] parties = new AssaultParty[2];
            parties[0] = (AssaultParty) Naming.lookup(rmiAddress(address, port, "assaultParty0"));
            parties[1] = (AssaultParty) Naming.lookup(rmiAddress(address, port, "assaultParty1"));

            ConcentrationSite concentration = (ConcentrationSite) Naming.lookup(rmiAddress(address, port, "concentration"));
            ControlCollectionSite controlCollection = (ControlCollectionSite) Naming.lookup(rmiAddress(address, port, "controlCollection"));
            Logger logger = (Logger) Naming.lookup(rmiAddress(address, port, "logger"));

            //OrdinaryThieves
            for(int i = 0; i < configuration.numberThieves; i++)
            {
                new OrdinaryThief(i, controlCollection, concentration, museum, parties, logger, configuration).start();
            }

            //MasterThief
            new MasterThief(controlCollection, concentration, museum, parties, logger, configuration).start();
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in RMI thieves (" + e + ")");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
