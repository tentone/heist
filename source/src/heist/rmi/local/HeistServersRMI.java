package heist.rmi.local;

import heist.concurrent.shared.SharedAssaultParty;
import heist.concurrent.shared.SharedConcentrationSite;
import heist.concurrent.shared.SharedControlCollectionSite;
import heist.concurrent.shared.SharedLogger;
import heist.concurrent.shared.SharedMuseum;
import heist.interfaces.*;
import heist.rmi.ConfigurationRMI;
import static heist.utils.Address.rmiAddress;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class HeistServersRMI
{
    public static void main(String[] args)
    {
        String address = (args.length > 0) ?  args[0] : "localhost";
        int port = (args.length > 1) ?  Integer.parseInt(args[1]) : 22399;
        
        try
        {
            ConfigurationRMI configuration = new ConfigurationRMI();

            Naming.rebind(rmiAddress(address, port, "museum"), UnicastRemoteObject.exportObject(new SharedMuseum(configuration), configuration.museumServer.port));
   
            Naming.rebind(rmiAddress(address, port, "assaultParty0"), UnicastRemoteObject.exportObject(new SharedAssaultParty(0, configuration), configuration.assaultPartiesServers[0].port));
            Naming.rebind(rmiAddress(address, port, "assaultParty1"), UnicastRemoteObject.exportObject(new SharedAssaultParty(1, configuration), configuration.assaultPartiesServers[1].port));

            Museum museum = (Museum) Naming.lookup(rmiAddress(address, port, "museum"));
            AssaultParty[] parties = new AssaultParty[2];
            parties[0] = (AssaultParty) Naming.lookup(rmiAddress(address, port, "assaultParty0"));
            parties[1] = (AssaultParty) Naming.lookup(rmiAddress(address, port, "assaultParty1"));

            Naming.rebind(rmiAddress(address, port, "controlCollection"), UnicastRemoteObject.exportObject(new SharedControlCollectionSite(parties, museum, configuration), configuration.controlCollectionServer.port));
            Naming.rebind(rmiAddress(address, port, "concentration"), UnicastRemoteObject.exportObject(new SharedConcentrationSite(parties, configuration), configuration.concentrationServer.port));

            ControlCollectionSite controlCollection = (ControlCollectionSite) Naming.lookup(rmiAddress(address, port, "controlCollection"));
            Naming.rebind(rmiAddress(address, port, "logger"), UnicastRemoteObject.exportObject(new SharedLogger(parties, museum, controlCollection, configuration), configuration.loggerServer.port));
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in RMI servers");
            e.printStackTrace();
            System.exit(1);
        }
        
        System.out.println("Info: Servers running");
    }
}
