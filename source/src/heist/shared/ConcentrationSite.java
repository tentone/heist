package heist.shared;

import heist.queue.LinkedQueue;
import heist.thief.OrdinaryThief;
import heist.RoomStatus;

/**
 * The concentration site is where OrdinaryThieves wait for the MasterThief to assign them a AssaultParty.
 * ConcentrationSite is accessed by the MasterThief to get OrdinaryThieves to create and join AssaultParties.
 * @author Jose Manuel
 */
public class ConcentrationSite
{
    private final LinkedQueue<OrdinaryThief> thieves;
    private final LinkedQueue<AssaultParty> parties;
    
    /**
     * ConcentrationSite constructor.
     */
    public ConcentrationSite()
    {   
        this.thieves = new LinkedQueue<>();
        this.parties = new LinkedQueue<>();
    }
    
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
