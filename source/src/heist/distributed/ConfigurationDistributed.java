package heist.distributed;

import heist.Configuration;

public class ConfigurationDistributed extends Configuration
{
    public final String concentrationSiteAddress = "localhost";
    public final int concentrationSitePort = 23290;
    
    public final String controlCollectionSiteAddress = "localhost";
    public final int controlCollectionSitePort = 23291;
    
    public ConfigurationDistributed()
    {
        super();
    }
}
