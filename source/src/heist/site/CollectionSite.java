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
    private final MasterThief master;
    private final Queue<OrdinaryThief> queue;

    /**
     * Collection site constructor
     * @param master Master thief.
     */
    public CollectionSite(MasterThief master)
    {   
        this.queue = new Queue<>();
        this.master = master;
    }
    
    /**
     * Add thief to the collection site and wake up the master thief.
     * The thief enters the queue wakes up the master thief and waits until is waken up.
     * @param thief Thief to be added.
     */
    public synchronized void enter(OrdinaryThief thief) throws InterruptedException
    {
        this.queue.push(thief);
        this.wait();
    }
    
    /**
     * Check if there is some thief waiting in the collection site.
     * @return True if there is some thief in the collection site.
     */
    public synchronized boolean hasThief()
    {
        return !queue.isEmpty();
    }
    
    /**
     * Function used to make somebody wait until a thief arrive here.
     * Used by the MasterThief to arrive until a OrdinaryThief arrives.
     * @throws InterruptedException 
     */
    public synchronized void waitUntilThiefArrives() throws InterruptedException
    {
        while(!this.hasThief())
        {
            this.wait();
        }
    }
    
    /**
     * Function to allow the master thief to get the canvas bough by OrdnaryThieves
     * The master thief wakes up
     */
    public synchronized void getCanvasFromThief() throws InterruptedException
    {
        if(this.hasThief())
        {
            OrdinaryThief thief = this.queue.pop();

            this.notify();
        }
        //TODO <ADD CODE HERE>
    }
}
