package heist.concurrent.shared;

import heist.Configuration;
import heist.queue.ArrayQueue;
import heist.queue.Queue;
import heist.concurrent.thief.OrdinaryThief;

/**
 * The concentration site is where OrdinaryThieves wait for the MasterThief to assign them a AssaultParty.
 * ConcentrationSite is accessed by the MasterThief to get OrdinaryThieves to create and join AssaultParties.
 * @author Jose Manuel
 */
public class ConcentrationSite implements heist.interfaces.ConcentrationSite
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
     * ConcentrationSite constructor.
     * @param configuration Configuration to be used.
     */
    public ConcentrationSite(Configuration configuration)
    {   
        this.waitingThieves = new ArrayQueue<>(configuration.numberThieves);
        this.waitingParties = new ArrayQueue<>(configuration.numberParties);
    }
    
    /**
     * Fill a Party of OrdinaryThieves with the thieves waiting in this ConcentrationSite.
     * The MasterThief creates the party and adds it to the waiting party list and gets locked until all thieves join the party.
     * The last thief to join the party wakes up the MasterThief.
     * @param party Party to be filled with thieves.
     * @throws java.lang.InterruptedException Exception
     */
    @Override
    public synchronized void fillAssaultParty(AssaultParty party) throws InterruptedException
    {
        this.waitingParties.push(party);
        this.notifyAll();

        while(!party.partyFull())
        {
            this.wait();
        }
        
        this.waitingParties.pop();
    }
    
    /**
     * OrdinaryThief enters the ConcentrationSite and joins the assault party.
     * The last OrdinaryThief to join the assault party wakes up the MasterThief that is waiting in createNewParty.
     * @param thief OrdinaryThief to add.
     * @throws java.lang.InterruptedException Exception
     */
    @Override
    public synchronized void prepareExcursion(OrdinaryThief thief) throws Exception
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
