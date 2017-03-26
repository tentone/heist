package heist.shared;

import heist.queue.Queue;
import heist.thief.OrdinaryThief;
import heist.RoomStatus;

/**
 * The concentration site is where OrdinaryThieves wait for the MasterThief to assign them a party.
 * Concentration site is accessed by the MasterThief to get OrdinaryThieves to create parties.
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
    
    /*
     * Check if there are enough OrdinaryThief to form a new AssaultParty.
     * @param partySize AssaultParty size.
     * @return True number of OrdinaryThief bigger or equal to SssaultParty size.
     */
    /*private synchronized boolean hasEnoughToCreateParty(int partySize)
    {
        return this.thieves.size() >= partySize;
    }*/
    
    /**
     * Create new Party of OrdinaryThieves with the thieves waiting in this ConcentrationSite.
     * The MasterThief creates the party and adds it to the party list and gets locked until all thieves join the party.
     * The last thief to join the party wakes up the MasterThief.
     * @param partySize AssaultParty size.
     * @param room Target room.
     * @param maxDistance Maximum distance allowed between thieves when crawling.
     * @return AssaultParty created.
     * @throws java.lang.InterruptedException Exception
     */
    public synchronized AssaultParty createNewParty(int partySize, int maxDistance, RoomStatus room) throws InterruptedException
    {
        AssaultParty party = new AssaultParty(partySize, maxDistance, room);

        this.parties.push(party);
        this.notifyAll();

        while(!party.partyFull())
        {
            this.wait();
        }

        this.parties.pop();

        return party;
    }
    
    /**
     * OrdinaryThief enters the ConcentrationSite and joins the assault party.
     * The last OrdinaryThief to join the assault party wakes up the MasterThief that is waiting in createNewParty.
     * @param thief OrdinaryThief to add.
     * @throws java.lang.InterruptedException Exception
     */
    public synchronized void prepareExcursion(OrdinaryThief thief) throws InterruptedException, Exception
    {
        this.thieves.push(thief);
        
        AssaultParty party = null;
        while(party == null)
        {
            party = this.parties.peek();
            if(party == null)
            {
                this.wait();
            }
        }
        
        this.thieves.pop();
        party.addThief(thief);
        
        if(party.partyFull())
        {
            this.notify();
        }
    }
}
