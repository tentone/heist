package heist.concurrent.shared;

import heist.Configuration;
import heist.queue.ArrayQueue;
import heist.queue.Queue;
import heist.room.RoomStatus;
import heist.concurrent.thief.OrdinaryThief;

/**
 * The concentration site is where OrdinaryThieves wait for the MasterThief to assign them a AssaultParty.
 * ConcentrationSite is accessed by the MasterThief to get OrdinaryThieves to create and join AssaultParties.
 * @author Jose Manuel
 */
public class ConcentrationSite implements heist.interfaces.ConcentrationSite
{    
    /**
     * AssaultParties.
     */
    private final AssaultParty[] parties;
    
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

        this.parties = new AssaultParty[configuration.numberParties];
        for(int i = 0; i < this.parties.length; i++)
        {
            this.parties[i] = new AssaultParty(configuration);
        }
    }
    
    /**
     * Get parties queue, contains the last parties created during this simulation.
     * These parties are used for logging.
     * @return Parties created during the simulation.
     */
    public synchronized AssaultParty[] getParties()
    {
        return this.parties;
    }

    /**
     * Check if there is some party available to be prepared and sent.
     * @return True if there is some party in DISMISSED state.
     */
    public synchronized boolean somePartyAvailable()
    {
        for(int i = 0; i < this.parties.length; i++)
        {
            if(this.parties[i].getState() == AssaultParty.DISMISSED)
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Create new Party of OrdinaryThieves with the thieves waiting in this ConcentrationSite.
     * The MasterThief creates the party and adds it to the party list and gets locked until all thieves join the party.
     * The last thief to join the party wakes up the MasterThief.
     * @param room Target room.
     * @return AssaultParty created.
     * @throws java.lang.InterruptedException Exception
     */
    @Override
    public synchronized AssaultParty prepareNewParty(RoomStatus room) throws InterruptedException
    {
        AssaultParty party = null;
        
        for(int i = 0; i < this.parties.length; i++)
        {
            if(this.parties[i].getState() == AssaultParty.DISMISSED)
            {
                party = this.parties[i];
                party.prepareParty(room);
                break;
            }
        }
        
        this.waitingParties.push(party);
        this.notifyAll();

        //TODO <REMOVE TRY CATCH>
        try
        {
            while(!party.partyFull())
            {
                this.wait();
            }
        }
        catch(Exception e)
        {
            System.out.println("-----------------------ERROR-----------------------");
            for(int i = 0; i < this.parties.length; i++)
            {
                System.out.println("Party " + i + ": " + this.parties[i].getState());
            }
            System.exit(1);
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
    @Override
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
