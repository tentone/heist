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
     * Check if there are enough OrdinaryThief to form a new AssaultParty.
     * @param partySize Assault party size.
     * @return True number of OrdinaryThief bigger or equal to SssaultParty size.
     */
    public synchronized boolean hasEnoughToCreateParty(int partySize)
    {
        return this.thieves.size() >= partySize;
    }
    
    /**
     * OrdinaryThief enters the ConcentrationSite and waits until a AssaultParty is attributed to him and waken up.
     * @param thief OrdinaryThief to add.
     * @throws java.lang.InterruptedException
     */
    public synchronized void enterAndWait(OrdinaryThief thief) throws InterruptedException
    {
        this.thieves.push(thief);
        
        this.wait();
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
