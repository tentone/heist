package heist.distributed;

import heist.Configuration;
import heist.utils.Address;
import java.io.File;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Distributed simulation configuration.
 * Inherits from the normal configuration and includes all simulation parameters.
 * @author Jose Manuel
 */
public class ConfigurationDistributed extends Configuration implements Serializable
{
    private static final long serialVersionUID = 61235823016L;
        
    /**
     * Logger server address.
     */
    public Address loggerServer;
    
    /**
     * Control and collection site server address.
     */
    public Address controlCollectionServer;
    
    /**
     * ConcentrationSite server address.
     */
    public Address concentrationServer;
    
    /**
     * Museum server address.
     */
    public Address museumServer;
    
    /**
     * AssaultParty server addresses.
     */
    public Address[] assaultPartiesServers;
    
    /**
     * Distributed configuration constructor.
     */
    public ConfigurationDistributed()
    {
        super();
        
        this.loggerServer = new Address("localhost", 23291, "logger");
        this.controlCollectionServer = new Address("localhost", 23292, "controlCollection");
        this.concentrationServer = new Address("localhost", 23293, "concentration");
        this.museumServer = new Address("localhost", 23294, "museum");
        
        int port = 23295;
        this.assaultPartiesServers = new Address[this.numberParties];
        for(int i = 0; i < this.assaultPartiesServers.length; i++)
        {
            this.assaultPartiesServers[i] = new Address("localhost", port + i, "assaultParty" + i);
        }
    }
    
    /**
     * Read configuration from file.
     * If file does not exist runs with local configuration.
     * @return ConfigurationDistributed instance with values from file.
     */
    public static ConfigurationDistributed readFromFile(String fname)
    {
        ConfigurationDistributed configuration = new ConfigurationDistributed();
        
        try
        {
            File file = new File(fname);
            Scanner scanner = new Scanner(file);
            
            String line = scanner.nextLine();
            String[] values = line.split(";");
            configuration.loggerServer.set(values[0], Integer.parseInt(values[1]), values[2]);
            
            line = scanner.nextLine();
            values = line.split(";");
            configuration.controlCollectionServer.set(values[0], Integer.parseInt(values[1]), values[2]);

            line = scanner.nextLine();
            values = line.split(";");
            configuration.concentrationServer.set(values[0], Integer.parseInt(values[1]), values[2]);
            
            line = scanner.nextLine();
            values = line.split(";");
            configuration.museumServer.set(values[0], Integer.parseInt(values[1]), values[2]);
            
            for(int i = 0; i < configuration.assaultPartiesServers.length; i++)
            {
                line = scanner.nextLine();
                values = line.split(";");
                configuration.assaultPartiesServers[i].set(values[0], Integer.parseInt(values[1]), values[2]);
            }
            
            System.out.println("Info: configuration.txt loaded");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Info: configuration.txt not found");
        }
        
        return configuration;
    }
}
