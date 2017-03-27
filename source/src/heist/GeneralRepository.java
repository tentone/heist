package heist;

import heist.log.Logger;
import heist.shared.Museum;
import heist.shared.ControlCollectionSite;
import heist.shared.ConcentrationSite;
import heist.thief.MasterThief;
import heist.thief.OrdinaryThief;

/**
 * The general repository stores all the components of the system. It is a shared memory region that is accessed by every active entity in the system.
 * It is also responsible for starting and killing all running instances.
 */
public class GeneralRepository
{
    private final Museum museum;
    private final ControlCollectionSite collection;
    private final ConcentrationSite concentration;
    
    private final MasterThief master;
    private final OrdinaryThief[] thieves;
    
    private final Logger logger;
    private final Configuration configuration;
    
    /**
     * General repository constructor
     * @param configuration Configuration for the simulation
     */
    public GeneralRepository(Configuration configuration)
    {
        this.configuration = configuration;
        this.logger = new Logger(this, configuration);
        
        this.museum = new Museum(this.configuration);
        this.concentration = new ConcentrationSite(this.configuration);
        this.collection = new ControlCollectionSite(this.configuration, this.museum);
        
        this.thieves = new OrdinaryThief[configuration.numberThieves];
        for(int i = 0; i < this.thieves.length; i++)
        {
            this.thieves[i] = new OrdinaryThief(i, this, this.configuration);
        }
        
        this.master = new MasterThief(this, this.configuration);
        
        this.logger.log();
    }
    
    /**
     * Initialise simulation elements and starts the simulation, calls the start method in all thieves
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
     * Return logger object.
     * @return Logger.
     */
    public Logger getLogger()
    {
        return this.logger;
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
     * @return CollectionSite.
     */
    public ControlCollectionSite getCollectionSite()
    {
        return this.collection;
    }
    
    /**
     * Get concentration site.
     * @return ConcentrationSite.
     */
    public ConcentrationSite getConcentrationSite()
    {
        return this.concentration;
    }
    
    /**
     * Get Master Thief.
     * @return MasterThief.
     */
    public MasterThief getMasterThief()
    {
        return this.master;
    }
    
    /**
     * Get Ordinary Thieves.
     * @return OrdinaryThief array.
     */
    public OrdinaryThief[] getOrdinaryThieves()
    {
        return this.thieves;
    }
}
