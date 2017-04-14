package heist.shared;

import heist.Configuration;
import heist.queue.ArrayQueue;
import heist.queue.LeakingQueue;
import heist.queue.Queue;
import heist.queue.iterator.Iterator;
import heist.room.RoomStatus;
import heist.thief.OrdinaryThief;

/**
 * The concentration site is where OrdinaryThieves wait for the MasterThief to assign them a AssaultParty.
 * ConcentrationSite is accessed by the MasterThief to get OrdinaryThieves to create and join AssaultParties.
 * @author Jose Manuel
 */
public class ConcentrationSite
{
    /**
     * List of thieves waiting to enter a team.
     */
    private final Queue<OrdinaryThief> waitingThieves;
    
    /**
     * List of parties waiting to be filled with thieves.
     */
    private final Queue<AssaultParty> waitingParties;
    
    /**
     * Array with the last created assault parties
     */
    private final Queue<AssaultParty> parties;

    /**
     * Maximum number of parties.
     */
    private final int numberParties;
    
    /**
     * ConcentrationSite constructor.
     * @param configuration Configuration to be used.
     */
    public ConcentrationSite(Configuration configuration)
    {   
        this.numberParties = configuration.numberThieves / configuration.partySize;
        
        this.waitingThieves = new ArrayQueue<>(configuration.numberThieves);
        this.waitingParties = new ArrayQueue<>(this.numberParties);

        this.parties = new LeakingQueue<>(this.numberParties);
    }
    
    /**
     * Get parties queue, contains the last parties created during this simulation.
     * These parties are used for logging.
     * @return Parties created during the simulation.
     */
    public synchronized AssaultParty[] getParties()
    {
        AssaultParty[] array = new AssaultParty[this.numberParties];
        int i = 0;
        
        Iterator<AssaultParty> it = this.parties.iterator();
        while(it.hasNext())
        {
            array[i++] = it.next();
        }
        
        return array;
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
        
        this.waitingParties.push(party);
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
