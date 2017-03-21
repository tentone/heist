package heist;

import heist.struct.Queue;
import heist.thief.MasterThief;
import heist.thief.OrdinaryThief;
import java.util.Iterator;

/**
 * The general repository stores all the components of the system.
 * It is also responsible for starting and killing all running instances.
 */
public class GeneralRepository
{   
    private MasterThief masterThief;
    private Queue<OrdinaryThief> thiefs;
    private Queue<Party> parties;
    private Museum museum;
    private CollectionSite collection;
    private ConcentrationSite concentration;
    
    /**
     * General repository constructor
     * 
     * @param museumSize
     * @param thiefCount
     * @param partySize 
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
     * Add a new party to the party list
     * @param party 
     */
    public synchronized void addParty(Party party)
    {
        this.parties.push(party);
    }
    
    /**
     * Get master thief object
     * @return 
     */
    public synchronized MasterThief getMasterThief()
    {
        return this.masterThief;
    }
    
    /**
     * Get museum object
     * @return 
     */
    public synchronized Museum getMuseum()
    {
        return this.museum;
    }
    
    /**
     * Get collection site object
     * @return 
     */
    public synchronized CollectionSite getCollectionSite()
    {
        return this.collection;
    }
    
    /**
     * Get concentration site object
     * @return 
     */
    public synchronized ConcentrationSite getConcentrationSite()
    {
        return this.concentration;
    }
}
