package heist.distributed.server.concentration;

import heist.distributed.communication.Message;

public class ConcentrationSiteMessage extends Message
{
    public static final int FILL_ASSAULT_PARTY = 13;
    
    public static final int PREPARE_EXCURSION = 14;
    
    public ConcentrationSiteMessage(int type)
    {
        super(type);
    }
}
