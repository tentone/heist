package heist;

import heist.concurrent.shared.SharedMuseum;
import heist.concurrent.shared.SharedControlCollectionSite;
import heist.concurrent.shared.SharedConcentrationSite;
import heist.concurrent.thief.MasterThief;
import heist.concurrent.thief.OrdinaryThief;

/**
 * The general repository stores all the components of the system. It is a shared memory region that is accessed by every active entity in the system.
 * It is also responsible for starting and killing all running instances.
 * @author Jose Manuel
 */
public class GeneralRepository
{
    /**
     * Museum to be assaulted by AssaultParties.
     */
    private final SharedMuseum museum;
    
    /**
     * The control and collection site.
     */
    private final SharedControlCollectionSite collection;
    
    /**
     * Concentration site where the OrdinaryThieves wait to be assigned to an AssaultParty.
     */
    private final SharedConcentrationSite concentration;
    
    /**
     * MasterThieve that controls and assigns OrdinaryThieves to AssaultParties.
     */
    private final MasterThief master;
    
    /**
     * OrdinaryThieves array
     */
    private final OrdinaryThief[] thieves;
    
    /**
     * Logger object used to log the state of this GeneralRepository
     */
    private final Logger logger;
    
    /**
     * Configuration used for the simulation.
     */
    private final Configuration configuration;
    
    /**
     * General repository constructor
     * @param configuration Configuration to be used to create elements in this repository.
     */
    public GeneralRepository(Configuration configuration)
    {
        this.configuration = configuration;
        this.logger = new Logger(this, configuration);
        
        this.museum = new SharedMuseum(this.configuration);
        this.concentration = new SharedConcentrationSite(this.configuration);
        this.collection = new SharedControlCollectionSite(this.configuration, this.museum);
        
        this.thieves = new OrdinaryThief[configuration.numberThieves];
        for(int i = 0; i < this.thieves.length; i++)
        {
            this.thieves[i] = new OrdinaryThief(i, this, this.configuration);
        }
        
        this.master = new MasterThief(this, this.configuration);

        this.logger.log();
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
    public SharedMuseum getMuseum()
    {
        return this.museum;
    }
    
    /**
     * Get collection site.
     * @return CollectionSite.
     */
    public SharedControlCollectionSite getControlCollectionSite()
    {
        return this.collection;
    }
    
    /**
     * Get concentration site.
     * @return ConcentrationSite.
     */
    public SharedConcentrationSite getConcentrationSite()
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
    
    /**
     * Create new log entry.
     */
    public void log()
    {
        this.logger.log();
    }
}
