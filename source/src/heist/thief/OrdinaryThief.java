package heist.thief;

import heist.Configuration;
import heist.GeneralRepository;
import heist.museum.Museum;
import heist.site.CollectionSite;
import heist.site.ConcentrationSite;

/**
 * OrdinaryThief represents a thief active entity.
 * Thieves enter the museum stole canvas and come back to return it to the MasterThief.
 * Thieves attack in parties defined by the MasterThief and cannot move their comrades behind.
 * After taking a step the thief wakes up the last thief in the Party queue to make a step.
 */
public class OrdinaryThief extends Thread
{
    private static int IDCounter = 0;
    
    private static final int OUTSIDE = 1000;
    private static final int CRAWLING_INWARDS = 2000;
    private static final int AT_A_ROOM = 3000;
    private static final int CRAWLING_OUTWARDS = 4000;

    private AssaultParty party;
    
    private ConcentrationSite concentration;
    private CollectionSite collection;
    private Museum museum;
    
    private final int id, maximumDisplacement;
    private int state, position;
    private boolean hasCanvas = false;

    /**
     * OrdinaryThief constructor.
     * @param repository General repository.
     * @param configuration Simulation configuration.
     */
    public OrdinaryThief(GeneralRepository repository, Configuration configuration)
    {
        this.id = IDCounter++;
        this.state = OUTSIDE;
        
        this.collection = repository.getCollectionSite();
        this.concentration = repository.getConcentrationSite();
        this.museum = repository.getMuseum();

        this.maximumDisplacement = configuration.thiefDisplacement.generateInRange();
        
        this.position = -1;
        this.hasCanvas = false;
        this.party = null;
    }
    
    /**
     * Get ordinary thief state
     * @return Thief state.
     */
    public int state()
    {
        return this.state;
    }

    /**
     * Check if the ordinary thief has a party.
     * @return True if ordinary thief is in a party
     */
    public boolean hasParty()
    {
        return this.party != null;
    }
    
    /**
     * Set assault party.
     * @param party Assault party.
     */
    public void setParty(AssaultParty party)
    {
        this.party = party;
    }
    
    /**
     * Get the thief position
     * @return Thief position, -1 if outside
     */
    public int getPosition()
    {
        return this.position;
    }
    
    /**
     * Get thief ID
     * @return Thief ID
     */
    public int getID()
    {
        return this.id;
    }
    
    /**
     * Make ordinary thief leave the party.
     */
    private void leaveParty()
    {
        this.party = null;
    }
    
    /**
     * Set thief state
     * @param state
     */
    private void setState(int state)
    {
        this.state = state;
    }
    
    /**
     * Prepare execution, assign party to thief and change state to crawling inwards and sets thief to sleep until the master thief or another thief wakes it up.
     */
    private void prepareExecution()
    {
        //TODO <GET ASSAULT PARTY>
        
        //TODO <GET OUT OF THE CONCENTRATION SITE>
        
        this.position = 0;
        this.setState(CRAWLING_INWARDS);
    }
    
    /**
     * Updates thief position inside the museum and set thief back to sleep, until another thief wakes it up.
     */
    private void crawlIn()
    {
        //TODO <ADD CODE HERE>
        
        //if(this.position == this.party.getTarget())
        //{
        //    this.rollACanvas();
        //}
    }
    
    /**
     * Try to collect a canvas from the room and reverse direction after.
     */
    private void rollACanvas()
    {
        //TODO <ADD CODE HERE>
        
        this.reverseDirection();
    }
    
    /**
     * Change state to crawling outwards.
     */
    private void reverseDirection()
    {
        this.setState(CRAWLING_OUTWARDS);
    }
    
    /**
     * Update position crawling out of the museum.
     */
    private void crawlOut()
    {
        //TODO <ADD CODE HERE>
    }
    
    /**
     * Hand the canvas (if there is one) to the master thief.
     */
    private void handACanvas()
    {
        //TODO <HAND THE CANVAS TO THE MASTER THIEF, ENTER THE COLLECTION SITE AND SLEEP>
    }
    
    /**
     * Check if the thief is still needed (check if every room has been emptied)
     */
    private boolean amINeeded()
    {
        //TODO <CHECK IF THE THIEF IS STILL NEEDED>
        return true;
    }
    
    /**
     * Thread run method
     */
    @Override
    public void run()
    {
        System.out.println("Thief " + this.id + " started");
        
        try
        {
            while(this.amINeeded())
            {
                this.prepareExecution();

                this.crawlIn();

                this.rollACanvas();
                this.reverseDirection();

                this.crawlOut();

                this.handACanvas();
            }
        }
        catch(Exception e)
        {
            System.out.println("Thief " + this.id + " error");
            e.printStackTrace();
        }
        
        System.out.println("Thief " + this.id + " terminated");
    }
}