package heist.distributed.configuration;

import heist.distributed.communication.Client;
import heist.utils.Address;

public class ConfigurationClient extends Client
{
    public ConfigurationClient(Address address)
    {
        super(address);
    }
}
