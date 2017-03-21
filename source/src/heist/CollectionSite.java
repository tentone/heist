package heist;

import heist.struct.Queue;
import heist.thief.MasterThief;
import heist.thief.OrdinaryThief;
import heist.utils.UUID;

public class CollectionSite
{
    private final MasterThief master;
    private final Queue<OrdinaryThief> queue;
    private final String uuid;
    
    public CollectionSite(MasterThief master)
    {   
        this.uuid = UUID.generate();
        this.queue = new Queue<>();
        this.master = master;
    }
    
    public void pushThief(OrdinaryThief thief)
    {
        //TODO <ADD CODE HERE>
    }
    
    public boolean hasThief()
    {
        return !queue.isEmpty();
    }
    
    public String getUUID()
    {
        return this.uuid;
    }
}
