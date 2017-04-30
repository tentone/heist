package heist.distributed.configuration;

import heist.distributed.communication.Message;
import java.io.Serializable;

public class ConfigurationMessage extends Message implements Serializable
{
    public static final int ASSAULT_PARTY = 50;
    
    public static final int MUSEUM = 51;
    
    public static final int CONTROL_COLLECTION_SITE = 52;
    
    public static final int CONCENTRATION_SITE = 53;
    
    public ConfigurationMessage(int type)
    {
        super(type);
    }
}
