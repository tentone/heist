package heist.distributed;

import heist.Configuration;
import heist.utils.Address;
import java.io.Serializable;

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
        
        this.loggerServer = new Address("localhost", 23291);
        this.controlCollectionServer = new Address("localhost", 23292);
        this.concentrationServer = new Address("localhost", 23293);
        this.museumServer = new Address("localhost", 23294);
        
        int port = 23295;
        this.assaultPartiesServers = new Address[this.numberParties];
        for(int i = 0; i < this.assaultPartiesServers.length; i++)
        {
            this.assaultPartiesServers[i] = new Address("localhost", port + i);
        }
    }
}
