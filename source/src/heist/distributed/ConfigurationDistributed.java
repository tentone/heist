package heist.distributed;

import heist.Configuration;
import heist.utils.Address;

public class ConfigurationDistributed extends Configuration
{
    public Address loggerServer;
    public Address controlCollectionServer;
    public Address concentrationServer;
    public Address museumServer;
    public Address assaultPartyAServer;
    public Address assaultPartyBServer;
    
    public ConfigurationDistributed()
    {
        super();
        
        this.loggerServer = new Address("localhost", 23291);
        this.controlCollectionServer = new Address("localhost", 23292);
        this.concentrationServer = new Address("localhost", 23293);
        this.museumServer = new Address("localhost", 23294);
        
        this.assaultPartyAServer = new Address("localhost", 23295);
        this.assaultPartyBServer = new Address("localhost", 23296);
    }
}
