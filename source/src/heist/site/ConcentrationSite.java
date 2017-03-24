package heist.site;

import heist.queue.Queue;
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
     * Check if there are enough thieves to form a new assault party.
     * @param partySize Assault party size.
     * @return True number of thieves bigger or equal to assault party size.
     */
    public synchronized boolean hasEnoughToCreateParty(int partySize)
    {
        return this.queue.size() >= partySize;
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
     * @return Removed thief.
     */
    public synchronized OrdinaryThief removeThief()
    {
        return this.queue.pop();
    }
}
