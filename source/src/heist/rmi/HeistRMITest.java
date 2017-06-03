package heist.rmi;

import heist.Configuration;
import heist.concurrent.shared.*;
import heist.interfaces.*;
import heist.thief.*;
import static heist.utils.Address.rmiAddress;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

/**
 * Simulation implementation using RMI.
 * @author Jose Manuel
 */
public class HeistRMITest
{
    private static Random random = new Random();
    
    public static void main(String[] args) throws Exception
    {
        Configuration configuration = new Configuration();

        Naming.rebind(rmiAddress("localhost", 5000, "museum"), UnicastRemoteObject.exportObject(new SharedMuseum(configuration), random.nextInt()));
        Naming.rebind(rmiAddress("localhost", 5000, "assaultParty0"), UnicastRemoteObject.exportObject(new SharedAssaultParty(0, configuration), random.nextInt()));
        Naming.rebind(rmiAddress("localhost", 5000, "assaultParty1"), UnicastRemoteObject.exportObject(new SharedAssaultParty(1, configuration), random.nextInt()));

        Museum museum = (Museum) Naming.lookup(rmiAddress("localhost", 5000, "museum"));
        
        AssaultParty[] parties = new AssaultParty[2];
        parties[0] = (AssaultParty) Naming.lookup(rmiAddress("localhost", 5000, "assaultParty0"));
        parties[1] = (AssaultParty) Naming.lookup(rmiAddress("localhost", 5000, "assaultParty1"));
        
        Naming.rebind(rmiAddress("localhost", 5000, "controlCollection"), UnicastRemoteObject.exportObject(new SharedControlCollectionSite(parties, museum, configuration), random.nextInt()));
        Naming.rebind(rmiAddress("localhost", 5000, "concentration"), UnicastRemoteObject.exportObject(new SharedConcentrationSite(parties, configuration), random.nextInt()));
       
        ConcentrationSite concentration = (ConcentrationSite) Naming.lookup(rmiAddress("localhost", 5000, "concentration"));
        ControlCollectionSite controlCollection = (ControlCollectionSite) Naming.lookup(rmiAddress("localhost", 5000, "controlCollection"));
        
        Naming.rebind(rmiAddress("localhost", 5000, "logger"), UnicastRemoteObject.exportObject(new SharedLogger(parties, museum, controlCollection, configuration), random.nextInt()));
        Logger logger = (Logger) Naming.lookup(rmiAddress("localhost", 5000, "logger"));

        //OrdinaryThieves
        for(int i = 0; i < configuration.numberThieves; i++)
        {
            new OrdinaryThief(i, controlCollection, concentration, museum, parties, logger, configuration).start();
        }
        
        //MasterThief
        new MasterThief(controlCollection, concentration, museum, parties, logger, configuration).start();
    }
}
