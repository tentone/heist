package heist;

import heist.struct.Queue;
import heist.thief.OrdinaryThief;
import heist.utils.UUID;

public class ConcentrationSite
{
    private final Queue<OrdinaryThief> queue;
    private final String uuid;
    
    public ConcentrationSite()
    {   
        this.uuid = UUID.generate();
        this.queue = new Queue<>();
    }
    
    public void addThief(OrdinaryThief thief)
    {
        this.queue.push(thief);
    }
    
    public String getUUID()
    {
        return this.uuid;
    }
}
