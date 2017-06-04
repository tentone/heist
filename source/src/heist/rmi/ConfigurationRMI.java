package heist.rmi;

import heist.distributed.ConfigurationDistributed;
import java.io.File;
import java.util.Scanner;

/**
 * Configuration for RMI based implementation.
 * @author Tentone
 */
public class ConfigurationRMI extends ConfigurationDistributed
{
    /**
     * Address for RMI registry server
     */
    public String rmiAddress;
    
    /**
     * Port for RMI registry server
     */
    public int rmiPort;
    
    /**
     * Constructor for RMI configuration.
     */
    public ConfigurationRMI()
    {
        super();
        
        this.rmiAddress = "localhost";
        this.rmiPort = 22399;
    }
    
    /**
     * Read configuration from file.
     * If file does not exist runs with local configuration.
     * @return ConfigurationDistributed instance with values from file.
     */
    public static ConfigurationRMI readFromFile(String fname)
    {
        ConfigurationRMI configuration = new ConfigurationRMI();
        
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
