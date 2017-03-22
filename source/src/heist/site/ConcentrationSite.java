package heist.site;

import heist.struct.Queue;
import heist.thief.OrdinaryThief;

/**
 * The concentration site is where thieves wait for the master thief to assign them a party.
 * Concentration site is accessed by the master thief to get thieves to create parties.
 * @author Jose Manuel
 */
public class ConcentrationSite
{
    private final Queue<OrdinaryThief> queue;
    
    /**
     * ConcentrationSite constructor.
     */
    public ConcentrationSite()
    {   
        this.queue = new Queue<>();
    }
    
    /**
     * Add thief to the concentration site.
     * @param thief Thief to add.
     */
    public synchronized void addThief(OrdinaryThief thief)
    {
        this.queue.push(thief);
    }
    
    /**
     * Check if there is some thief in the concentration site.
     * @return True if not empty
     */
    public synchronized boolean hasThief()
    {
        return !this.queue.isEmpty();
    }
    
    /**
     * Remove thief from the concentration site.
     * @return OrdinaryThief object.
     */
    public synchronized OrdinaryThief removeThief()
    {
        return this.queue.pop();
    }
}
