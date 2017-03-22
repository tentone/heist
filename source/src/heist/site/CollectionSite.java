package heist.site;

import heist.struct.Queue;
import heist.thief.MasterThief;
import heist.thief.OrdinaryThief;

/**
 * The collection site is where the OrdinaryThiefs deliver the canvas to the MasterThief
 * @author Jose Manuel
 */
public class CollectionSite
{
    private MasterThief master;
    private Queue<OrdinaryThief> queue;

    /**
     * Collection site constructor
     * @param master 
     */
    public CollectionSite(MasterThief master)
    {   
        this.queue = new Queue<>();
        this.master = master;
    }
    
    /**
     * Add thief to the collection site and wake up the master thief
     * @param thief 
     */
    public synchronized void addThief(OrdinaryThief thief)
    {
        //TODO <ADD CODE HERE>
    }
    
    /**
     * Check if there is some thief waiting in the collection site
     * @return True if there is some thief in the collection site
     */
    public synchronized boolean hasThief()
    {
        return !queue.isEmpty();
    }
}
