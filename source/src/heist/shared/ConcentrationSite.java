package heist.shared;

import heist.queue.Queue;
import heist.thief.OrdinaryThief;

/**
 * The concentration site is where thieves wait for the master thief to assign them a party.
 * Concentration site is accessed by the master thief to get thieves to create parties.
 * @author Jose Manuel
 */
public class ConcentrationSite
{
    private final Queue<OrdinaryThief> thieves;
    private final Queue<AssaultParty> parties;
    
    /**
     * ConcentrationSite constructor.
     */
    public ConcentrationSite()
    {   
        this.thieves = new Queue<>();
        this.parties = new Queue<>();
    }
    
    /**
     * Add thief to the concentration site.
     * @param thief Thief to add.
     */
    public synchronized void addThief(OrdinaryThief thief)
    {
        this.thieves.push(thief);
    }

    /**
     * Check if there are enough thieves to form a new assault party.
     * @param partySize Assault party size.
     * @return True number of thieves bigger or equal to assault party size.
     */
    public synchronized boolean hasEnoughToCreateParty(int partySize)
    {
        return this.thieves.size() >= partySize;
    }
    
    /**
     * OrdinaryThief waits until a party is attributed to him.
     * @return AssaultParty attributed to that ordinary thief.
     */
    public synchronized AssaultParty waitForPary()
    {
        //TODO <ADD CODE HERE>
        return null;
    }
    
    /**
     * Check if there is some thief in the concentration site.
     * @return True if not empty
     */
    public synchronized boolean hasThief()
    {
        return !this.thieves.isEmpty();
    }
    
    /**
     * Remove thief from the concentration site.
     * @return Removed thief.
     */
    public synchronized OrdinaryThief removeThief()
    {
        return this.thieves.pop();
    }
}
