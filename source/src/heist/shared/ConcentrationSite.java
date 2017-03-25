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
    
    /**
     * ConcentrationSite constructor.
     */
    public ConcentrationSite()
    {   
        this.thieves = new Queue<>();
    }

    /**
     * Check if there are enough OrdinaryThief to form a new AssaultParty.
     * @param partySize AssaultParty size.
     * @return True number of OrdinaryThief bigger or equal to SssaultParty size.
     */
    public synchronized boolean hasEnoughToCreateParty(int partySize)
    {
        return this.thieves.size() >= partySize;
    }
    
    /**
     * OrdinaryThief enters the ConcentrationSite and waits until a AssaultParty is attributed to him and waken up.
     * @param thief OrdinaryThief to add.
     * @throws java.lang.InterruptedException Exception
     */
    public synchronized void enterAndWait(OrdinaryThief thief) throws InterruptedException
    {
        this.thieves.push(thief);
        this.wait();
    }
    
    /**
     * Cerate new Party of OrdinaryThieves with the thieves waiting in this ConcentrationSite.
     * @param size AssaultParty size.
     * @param target Target room id.
     * @param targetDistance Target room distance.
     * @return AssaultParty created.
     */
    public synchronized AssaultParty createNewParty(int size, int target, int targetDistance, int maxDistance)
    {
        AssaultParty party = new AssaultParty(size, target, targetDistance, maxDistance);
        
        for(int i = 0; i < size; i++)
        {
            party.addThief(this.thieves.pop());
            this.notify();
        }
        
        return party;
    }
}
