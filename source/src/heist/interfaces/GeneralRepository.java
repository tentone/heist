package heist.interfaces;

import heist.thief.*;

/**
 * The general repository stores all the components of the system. It is a shared memory region that is accessed by every active entity in the system.
 * It is also responsible for starting and killing all running instances.
 * @author Jose Manuel
 */
public interface GeneralRepository
{
    /**
     * Return logger object.
     * @return Logger.
     */
    public Logger getLogger();
    
    /**
     * Get museum.
     * @return Museum
     */
    public Museum getMuseum();
    
    /**
     * Get collection site.
     * @return CollectionSite.
     */
    public ControlCollectionSite getControlCollectionSite();
    
    /**
     * Get concentration site.
     * @return ConcentrationSite.
     */
    public ConcentrationSite getConcentrationSite();
    
    /**
     * Get Master Thief.
     * @return MasterThief.
     */
    public MasterThief getMasterThief();
    
    /**
     * Get Ordinary Thieves.
     * @return OrdinaryThief array.
     */
    public OrdinaryThief[] getOrdinaryThieves();
}
