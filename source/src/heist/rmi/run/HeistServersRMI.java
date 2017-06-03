package heist.rmi.run;

import heist.Configuration;
import heist.concurrent.shared.SharedAssaultParty;
import heist.concurrent.shared.SharedConcentrationSite;
import heist.concurrent.shared.SharedControlCollectionSite;
import heist.concurrent.shared.SharedLogger;
import heist.concurrent.shared.SharedMuseum;
import heist.interfaces.*;
import static heist.utils.Address.rmiAddress;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class HeistServersRMI
{
    public static void main(String[] args) throws Exception
    {
        int port = 4000;
        
        Configuration configuration = new Configuration();

        Naming.rebind(rmiAddress("localhost", 5000, "museum"), UnicastRemoteObject.exportObject(new SharedMuseum(configuration), port++));
        Naming.rebind(rmiAddress("localhost", 5000, "assaultParty0"), UnicastRemoteObject.exportObject(new SharedAssaultParty(0, configuration), port++));
        Naming.rebind(rmiAddress("localhost", 5000, "assaultParty1"), UnicastRemoteObject.exportObject(new SharedAssaultParty(1, configuration), port++));

        Museum museum = (Museum) Naming.lookup(rmiAddress("localhost", 5000, "museum"));
        AssaultParty[] parties = new AssaultParty[2];
        parties[0] = (AssaultParty) Naming.lookup(rmiAddress("localhost", 5000, "assaultParty0"));
        parties[1] = (AssaultParty) Naming.lookup(rmiAddress("localhost", 5000, "assaultParty1"));
        
        Naming.rebind(rmiAddress("localhost", 5000, "controlCollection"), UnicastRemoteObject.exportObject(new SharedControlCollectionSite(parties, museum, configuration), port++));
        Naming.rebind(rmiAddress("localhost", 5000, "concentration"), UnicastRemoteObject.exportObject(new SharedConcentrationSite(parties, configuration), port++));
       
        ControlCollectionSite controlCollection = (ControlCollectionSite) Naming.lookup(rmiAddress("localhost", 5000, "controlCollection"));
        Naming.rebind(rmiAddress("localhost", 5000, "logger"), UnicastRemoteObject.exportObject(new SharedLogger(parties, museum, controlCollection, configuration), port++));
    }
}
