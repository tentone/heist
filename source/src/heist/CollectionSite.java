package heist;

import heist.struct.Queue;
import heist.thief.MasterThief;
import heist.thief.OrdinaryThief;
import heist.utils.UUID;

//The collection site is where the OrdinaryThiefs deliver the canvas to the MasterThief
public class CollectionSite
{
    private MasterThief master;
    private Queue<OrdinaryThief> queue;

    public CollectionSite(MasterThief master)
    {   
        this.queue = new Queue<>();
        this.master = master;
    }
    
    public synchronized void pushThief(OrdinaryThief thief)
    {
        //TODO <ADD CODE HERE>
    }
    
    public synchronized boolean hasThief()
    {
        return !queue.isEmpty();
    }
}
