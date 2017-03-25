package heist.shared.site;

import heist.queue.Queue;
import heist.shared.AssaultParty;
import heist.thief.OrdinaryThief;

/**
 * The concentration site is where thieves wait for the master thief to assign them a party.
 * Concentration site is accessed by the master thief to get thieves to create parties.
 * @author Jose Manuel
 */
public class ConcentrationSite
{
    private final Queue<OrdinaryThief> thieves;
    private boolean allRoomsClear;
    
    /**
     * ConcentrationSite constructor.
     */
    public ConcentrationSite()
    {   
        this.thieves = new Queue<>();
        this.allRoomsClear = false;
    }
    
    /**
     * Check if all rooms are clear to see if an OrdinaryThief can terminate.
     * @return True if there are still rooms waiting to be cleared
     */
    public synchronized boolean amINeeded()
    {
        return !this.allRoomsClear;
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
    public synchronized void prepareExcursion(OrdinaryThief thief) throws InterruptedException
    {
        this.thieves.push(thief);
        
        while(!thief.hasParty())
        {
            this.wait();
        }
    }
    
    /**
     * Create new Party of OrdinaryThieves with the thieves waiting in this ConcentrationSite.
     * @param size AssaultParty size.
     * @param target Target room id.
     * @param targetDistance Target room distance.
     * @param maxDistance Maximum distance allowed between thieves when crawling.
     * @return AssaultParty created.
     * @throws java.lang.InterruptedException Exception
     */
    public synchronized AssaultParty createNewParty(int size, int target, int targetDistance, int maxDistance) throws InterruptedException
    {
        AssaultParty party = new AssaultParty(size, target, targetDistance, maxDistance);
        
        for(int i = 0; i < size; i++)
        {
            party.addThief(this.thieves.pop());
            this.notify();
        }
        
        return party;
    }
    
    /**
     * Called by the MasterThief to terminate the heist.
     */
    public synchronized void allRoomsClear()
    {
        this.allRoomsClear = true;
    }
}
