package heist.shared;

import heist.queue.Queue;
import heist.thief.OrdinaryThief;
import heist.RoomStatus;

/**
 * The concentration site is where thieves wait for the master thief to assign them a party.
 * Concentration site is accessed by the master thief to get thieves to create parties.
 * @author Jose Manuel
 */
public class ConcentrationSite
{
    private final Queue<OrdinaryThief> thieves;
    private final Queue<AssaultParty> parties;
    private boolean roomsClear;
    
    /**
     * ConcentrationSite constructor.
     */
    public ConcentrationSite()
    {   
        this.thieves = new Queue<>();
        this.parties = new Queue<>();
        this.roomsClear = false;
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
     * Check if the OrdinaryThief is still needed or if he can be terminated.
     * @throws java.lang.InterruptedException Exception
     * @return True if the OrdinaryThief is still needed
     */
    public synchronized boolean amINeeded() throws InterruptedException
    {
        if(this.roomsClear)
        {
            return false;
        }
        
        return true;
    }
    
    /**
     * OrdinaryThief enters the ConcentrationSite and waits until a AssaultParty is attributed to him and waken up.
     * @param thief OrdinaryThief to add.
     * @throws java.lang.InterruptedException Exception
     */
    public synchronized void prepareExcursion(OrdinaryThief thief) throws InterruptedException
    {
        this.thieves.push(thief);

        this.wait();
    }
    
    /**
     * Create new Party of OrdinaryThieves with the thieves waiting in this ConcentrationSite.
     * @param partySize AssaultParty size.
     * @param room Target room.
     * @param maxDistance Maximum distance allowed between thieves when crawling.
     * @return AssaultParty created.
     * @throws java.lang.InterruptedException Exception
     */
    public synchronized AssaultParty createNewParty(int partySize, int maxDistance, RoomStatus room) throws InterruptedException
    {
        AssaultParty party = new AssaultParty(partySize, maxDistance, room);
        
        for(int i = 0; i < partySize; i++)
        {
            try
            {
                party.addThief(this.thieves.pop());
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            this.notify();
        }
        
        this.parties.push(party);
        return party;
    }
    
    /**
     * Called by the MasterThief to indicate that the heist is over.
     */
    public synchronized void sumUpResults()
    {
        this.roomsClear = true;
        this.notifyAll();
    }
}
