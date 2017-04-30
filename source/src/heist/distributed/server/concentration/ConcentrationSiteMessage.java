package heist.distributed.server.concentration;

import heist.distributed.communication.Message;
import heist.thief.OrdinaryThief;
import java.io.Serializable;

public class ConcentrationSiteMessage extends Message implements Serializable
{
    private static final long serialVersionUID = 8363128352672228L;
    
    public static final int FILL_ASSAULT_PARTY = 13;
    
    public static final int PREPARE_EXCURSION = 14;
    
    public int party;
    public OrdinaryThief thief;
    
    public ConcentrationSiteMessage(int type)
    {
        super(type);
    }
}
