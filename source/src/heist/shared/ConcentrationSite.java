package heist.shared;

import heist.Configuration;
import heist.queue.LinkedQueue;
import heist.thief.OrdinaryThief;
import heist.RoomStatus;
import heist.queue.Iterator;

/**
 * The concentration site is where OrdinaryThieves wait for the MasterThief to assign them a AssaultParty.
 * ConcentrationSite is accessed by the MasterThief to get OrdinaryThieves to create and join AssaultParties.
 * @author Jose Manuel
 */
public class ConcentrationSite
{
    private final LinkedQueue<OrdinaryThief> waitingThieves;
    private final LinkedQueue<AssaultParty> waitingParties, parties;
    private final int patiesToStore;
    
    /**
     * ConcentrationSite constructor.
     */
    public ConcentrationSite(Configuration configuration)
    {   
        this.waitingThieves = new LinkedQueue<>();
        this.waitingParties = new LinkedQueue<>();
        this.parties = new LinkedQueue<>();
        this.patiesToStore = configuration.numberThieves / configuration.partySize;
    }
    
    /**
     * Get parties queue, contains the last parties created during this simulation.
     * @return Parties created during the simulation
     */
    public synchronized Iterator<AssaultParty> getParties()
    {
        return this.parties.iterator();
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

        this.waitingParties.push(party);
        this.parties.push(party);
        if(this.parties.size() > this.patiesToStore)
        {
            this.parties.pop();
        }
        
        this.notifyAll();

        while(!party.partyFull())
        {
            this.wait();
        }

        this.waitingParties.pop();

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
        this.waitingThieves.push(thief);

        AssaultParty party = null;
        while(party == null)
        {
            party = this.waitingParties.peek();
            if(party == null)
            {
                this.wait();
            }
        }
        
        this.waitingThieves.pop();
        party.addThief(thief);
        
        if(party.partyFull())
        {
            this.notify();
        }
    }
}
