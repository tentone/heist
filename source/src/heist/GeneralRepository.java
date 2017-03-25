package heist;

import heist.log.Logger;
import heist.shared.Museum;
import heist.shared.site.CollectionSite;
import heist.shared.site.ConcentrationSite;
import heist.thief.MasterThief;
import heist.thief.OrdinaryThief;

/**
 * The general repository stores all the components of the system. It is a shared memory region that is accessed by every active entity in the system.
 * It is also responsible for starting and killing all running instances.
 */
public class GeneralRepository
{
    private Museum museum;
    private CollectionSite collection;
    private ConcentrationSite concentration;
    
    private MasterThief master;
    private OrdinaryThief[] thieves;
    
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
        
        this.museum = new Museum(this.configuration);
        this.concentration = new ConcentrationSite();
        this.collection = new CollectionSite();
        
        this.thieves = new OrdinaryThief[configuration.numberThieves];
        for(int i = 0; i < this.thieves.length; i++)
        {
            this.thieves[i] = new OrdinaryThief(this, this.configuration);
        }
        
        this.master = new MasterThief(this, this.configuration);
    }
    
    /**
     * Initialize simulation elements and starts the simulation, calls the start method in all thieves
     */
    public void start()
    {
        for(int i = 0; i < this.thieves.length; i++)
        {
            this.thieves[i].start();
        }

        this.master.start();
    }
    
    /**
     * Generate new log entry.
     */
    public synchronized void log()
    {
        this.logger.log();
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
