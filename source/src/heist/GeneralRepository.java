package heist;

import heist.struct.Queue;
import heist.thief.MasterThief;
import heist.thief.OrdinaryThief;
import java.util.Iterator;

/**
 * The general repository stores all the components of the system.
 * It is also responsible for starting and killing all running instances.
 * 
 * @author Tentone
 */
public class GeneralRepository
{   
    private MasterThief master;
    private Queue<OrdinaryThief> thiefs;
    
    private Museum museum;
    private CollectionSite collection;
    private ConcentrationSite concentration;
    
    public GeneralRepository(int museumSize, int thiefCount, int partySize)
    {
        museum = new Museum(museumSize);
        
        thiefs = new Queue<>();
        concentration = new ConcentrationSite();
        for(int i = 0; i < thiefCount; i++)
        {
            OrdinaryThief thief = new OrdinaryThief();
            thiefs.push(thief);
            concentration.addThief(thief);
        }
        
        master = new MasterThief();
        collection = new CollectionSite(master);
    }
    
    public void start()
    {
        master.start();
        
        Iterator<OrdinaryThief> it = thiefs.iterator();
        while(it.hasNext())
        {
            it.next().start();
        }
    }
    
    public MasterThief getMasterThief()
    {
        return master;
    }
    
    public Museum getMuseum()
    {
        return museum;
    }
    
    public CollectionSite getCollectionSite()
    {
        return collection;
    }
    
    public ConcentrationSite getConcentrationSite()
    {
        return concentration;
    }
}
