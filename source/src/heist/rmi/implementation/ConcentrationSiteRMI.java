package heist.rmi.implementation;

import heist.Configuration;
import heist.queue.ArrayQueue;
import heist.queue.Queue;
import heist.thief.OrdinaryThief;
import heist.interfaces.AssaultParty;
import heist.interfaces.ConcentrationSite;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The concentration site is where OrdinaryThieves wait for the MasterThief to assign them a AssaultParty.
 * ConcentrationSite is accessed by the MasterThief to get OrdinaryThieves to create and join AssaultParties.
 * @author Jose Manuel
 */
public class ConcentrationSiteRMI extends UnicastRemoteObject implements ConcentrationSite, Serializable
{
    private static final long serialVersionUID = 4723935792666454L;

    /**
     * AssaultParties
     */
    private final AssaultParty[] parties;
    
    /**
     * Simulation configuration.
     */
    private final Configuration configuration;
    
    /**
     * List of thieves waiting to enter a team.
     */
    private final Queue<OrdinaryThief> waitingThieves;
    
    /**
     * List of parties waiting to be filled with thieves.
     */
    private final Queue<Integer> waitingParties;

    /**
     * ConcentrationSite constructor.
     * @param parties AssaultParties
     * @param configuration Configuration to be used.
     */
    public ConcentrationSiteRMI(AssaultParty[] parties, Configuration configuration) throws RemoteException
    {   
        super();
        
        this.parties = parties;
        this.configuration = configuration;
        
        this.waitingThieves = new ArrayQueue<>(configuration.numberThieves);
        this.waitingParties = new ArrayQueue<>(configuration.numberParties);
    }
    
    /**
     * Fill a Party of OrdinaryThieves with the thieves waiting in this ConcentrationSite.
     * The MasterThief creates the party and adds it to the waiting party list and gets locked until all thieves join the party.
     * The last thief to join the party wakes up the MasterThief.
     * @param partyID ID of the AssaultParty to be filled with thieves.
     * @throws Exception Exception
     */
    @Override
    public synchronized void fillAssaultParty(int partyID) throws Exception
    {
        if(this.waitingParties.contains(partyID))
        {
            throw new Exception("Party already in the list " + this.waitingParties.toString() + " (" + partyID + ")");
        }
        
        this.waitingParties.push(partyID);
        this.notifyAll();
        
        while(!this.parties[partyID].partyFull())
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
    public synchronized int prepareExcursion(OrdinaryThief thief) throws Exception
    {
        this.waitingThieves.push(thief);

        while(this.waitingParties.size() == 0)
        {
            this.wait();
        }
        
        int partyID = this.waitingParties.peek();
        
        this.waitingThieves.pop();
        this.parties[partyID].addThief(thief);
        
        if(this.parties[partyID].partyFull())
        {
            this.notifyAll();
        }
        
        return partyID;
    }
}
