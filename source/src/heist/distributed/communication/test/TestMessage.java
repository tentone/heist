package heist.distributed.communication.test;

import heist.distributed.communication.Message;
import heist.thief.OrdinaryThief;
import java.io.Serializable;

public class TestMessage extends Message implements Serializable
{
    public OrdinaryThief thief = null;
    
    public TestMessage(int type)
    {
        super(type);
    }
}
