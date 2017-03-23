package heist;

import heist.log.Logger;
import heist.museum.Museum;
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
    
    private Logger logger;
    private Configuration configuration;
    
    /**
     * General repository constructor
     * @param configuration Configuration for the simulation
     */
    public GeneralRepository(Configuration configuration)
    {
        this.logger = new Logger(this);
        this.configuration = configuration;
        
        this.initialize();
    }
    
    /**
     * Initialise simulation elements.
     */
    public void initialize()
    {
        this.museum = new Museum(this.configuration);
        
        this.thiefs = new Queue<>();
        this.parties = new Queue<>();
        
        this.masterThief = new MasterThief(this);
        this.concentration = new ConcentrationSite();
        this.collection = new CollectionSite(masterThief);
        
        for(int i = 0; i < this.configuration.numberThieves; i++)
        {
            OrdinaryThief thief = new OrdinaryThief(this, this.configuration);
            this.thiefs.push(thief);
            this.concentration.addThief(thief);
        }
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
     * Generate new log entry.
     */
    public synchronized void log()
    {
        this.logger.log();
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
