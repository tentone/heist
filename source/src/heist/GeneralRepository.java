package heist;

import heist.thief.AssaultParty;
import heist.site.CollectionSite;
import heist.site.ConcentrationSite;
import heist.struct.Queue;
import heist.thief.MasterThief;
import heist.thief.OrdinaryThief;
import java.util.Iterator;

/**
 * The general repository stores all the components of the system. It is a shared memory region that is accessed by every active entity in the system.
 * It is also responsible for starting and killing all running instances.
 */
public class GeneralRepository
{   
    private MasterThief masterThief;
    private Queue<OrdinaryThief> thiefs;
    private Queue<AssaultParty> parties;
    private Museum museum;
    private CollectionSite collection;
    private ConcentrationSite concentration;
    
    /**
     * General repository constructor
     * @param museumSize The museum size
     * @param thiefCount How many thieves to create
     * @param partySize The party size for reach assault party
     */
    public GeneralRepository(int museumSize, int thiefCount, int partySize)
    {
        this.museum = new Museum(museumSize);
        
        this.thiefs = new Queue<>();
        this.concentration = new ConcentrationSite();
        for(int i = 0; i < thiefCount; i++)
        {
            OrdinaryThief thief = new OrdinaryThief();
            this.thiefs.push(thief);
            this.concentration.addThief(thief);
        }
        
        this.parties = new Queue<>();
        
        this.masterThief = new MasterThief();
        this.collection = new CollectionSite(masterThief);
    }
    
    /**
     * Starts the simulation, calls the start method in all thieves
     */
    public void start()
    {
       this.masterThief.start();
        
        Iterator<OrdinaryThief> it = this.thiefs.iterator();
        while(it.hasNext())
        {
            it.next().start();
        }
    }
    
    /**
     * Add a new party to the party list.
     * @param party Party to be added to the list.
     */
    public synchronized void addParty(AssaultParty party)
    {
        this.parties.push(party);
    }
    
    /**
     * Get master thief.
     * @return MasterThief
     */
    public MasterThief getMasterThief()
    {
        return this.masterThief;
    }
    
    /**
     * Get museum.
     * @return Museum
     */
    public Museum getMuseum()
    {
        return this.museum;
    }
    
    /**
     * Get collection site.
     * @return CollectionSite
     */
    public CollectionSite getCollectionSite()
    {
        return this.collection;
    }
    
    /**
     * Get concentration site.
     * @return ConcentrationSite
     */
    public ConcentrationSite getConcentrationSite()
    {
        return this.concentration;
    }
}
