package heist.shared;

import heist.queue.Queue;
import heist.thief.MasterThief;
import heist.thief.OrdinaryThief;

/**
 * The collection site is where the OrdinaryThiefs deliver the canvas to the MasterThief
 * @author Jose Manuel
 */
public class ControlCollectionSite
{
    private final Queue<OrdinaryThief> queue;

    /**
     * Collection site constructor, creates a queue for OrdinaryThief.
     */
    public ControlCollectionSite()
    {   
        this.queue = new Queue<>();
    }
    
    /**
     * Check if there is some thief waiting in the collection site.
     * @return True if there is some thief in the collection site.
     */
    public synchronized boolean hasThief()
    {
        return !this.queue.isEmpty();
    }
        
    /**
     * Function used to make MasterThief wait until a OrdinaryThief arrives and wakes him up.
     * @throws InterruptedException Exception
     */
    public synchronized void takeARest() throws InterruptedException
    {
        while(!this.hasThief())
        {
            this.wait();
        }
    }
    
    /**
     * Add thief to the collection site and wake up the MasterThief to collect its canvas.
     * The OrdinaryThief enters the queue wakes up the MasterThief and waits until is waken up.
     * @param thief Thief to be added.
     * @throws java.lang.InterruptedException Exception.
     */
    public synchronized void handACanvas(OrdinaryThief thief) throws InterruptedException
    {
        this.queue.push(thief);
        this.notify();
        
        this.wait();
    }
    
    /**
     * Function to allow the MasterThief to get the canvas bough by OrdinaryThieves.
     * The MasterThief wakes up the OrdinaryThieves waiting for their canvas to be collected.
     * @param master MasterThief to collect the canvas and update internal RoomStatus.
     * @throws java.lang.InterruptedException Exception
     */
    public synchronized void collectCanvas(MasterThief master) throws InterruptedException
    {
        while(!this.queue.isEmpty())
        {
            OrdinaryThief thief = this.queue.pop();
            master.collectCanvas(thief.getParty().getTarget(), thief.deliverCanvas());
            this.notify();
        }
    }
}
