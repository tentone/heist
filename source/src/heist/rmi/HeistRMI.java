package heist.rmi;

import heist.concurrent.shared.*;
import heist.distributed.ConfigurationDistributed;
import heist.interfaces.*;
import heist.thief.*;
import static heist.utils.Address.rmiAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class HeistRMI
{
    public static void main(String[] args) throws Exception
    {
        String address = (args.length > 0) ?  args[0] : "localhost";
        int port = (args.length > 1) ?  Integer.parseInt(args[1]) : 22399;
        boolean createRegistry = (args.length > 2) ?  Boolean.parseBoolean(args[2]) : true;
        
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
        
        ConfigurationDistributed configuration = new ConfigurationDistributed();

        Naming.rebind(rmiAddress(address, port, "museum"), UnicastRemoteObject.exportObject(new SharedMuseum(configuration), configuration.museumServer.port));
        Naming.rebind(rmiAddress(address, port, "assaultParty0"), UnicastRemoteObject.exportObject(new SharedAssaultParty(0, configuration), configuration.assaultPartiesServers[0].port));
        Naming.rebind(rmiAddress(address, port, "assaultParty1"), UnicastRemoteObject.exportObject(new SharedAssaultParty(1, configuration), configuration.assaultPartiesServers[1].port));

        Museum museum = (Museum) Naming.lookup(rmiAddress(address, port, "museum"));
        
        AssaultParty[] parties = new AssaultParty[2];
        parties[0] = (AssaultParty) Naming.lookup(rmiAddress(address, port, "assaultParty0"));
        parties[1] = (AssaultParty) Naming.lookup(rmiAddress(address, port, "assaultParty1"));
        
        Naming.rebind(rmiAddress(address, port, "controlCollection"), UnicastRemoteObject.exportObject(new SharedControlCollectionSite(parties, museum, configuration), configuration.controlCollectionServer.port));
        Naming.rebind(rmiAddress(address, port, "concentration"), UnicastRemoteObject.exportObject(new SharedConcentrationSite(parties, configuration), configuration.concentrationServer.port));

        ConcentrationSite concentration = (ConcentrationSite) Naming.lookup(rmiAddress(address, port, "concentration"));
        ControlCollectionSite controlCollection = (ControlCollectionSite) Naming.lookup(rmiAddress(address, port, "controlCollection"));
        
        Naming.rebind(rmiAddress(address, port, "logger"), UnicastRemoteObject.exportObject(new SharedLogger(parties, museum, controlCollection, configuration), configuration.loggerServer.port));
        Logger logger = (Logger) Naming.lookup(rmiAddress(address, port, "logger"));

        //OrdinaryThieves
        for(int i = 0; i < configuration.numberThieves; i++)
        {
            new OrdinaryThief(i, controlCollection, concentration, museum, parties, logger, configuration).start();
        }
        
        //MasterThief
        new MasterThief(controlCollection, concentration, museum, parties, logger, configuration).start();
    }
}
