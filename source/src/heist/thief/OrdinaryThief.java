package heist.thief;

import heist.shared.AssaultParty;
import heist.Configuration;
import heist.GeneralRepository;
import heist.shared.Museum;
import heist.shared.CollectionSite;
import heist.shared.ConcentrationSite;

/**
 * OrdinaryThief represents a thief active entity.
 * Thieves enter the museum stole canvas and come back to return it to the MasterThief.
 * Thieves attack in parties defined by the MasterThief and cannot move their comrades behind.
 * After taking a step the thief wakes up the last thief in the Party queue to make a step.
 */
@SuppressWarnings("empty-statement")
public class OrdinaryThief extends Thread
{
    private static int IDCounter = 0;
    
    public static final int OUTSIDE = 1000;
    public static final int CRAWLING_INWARDS = 2000;
    public static final int AT_A_ROOM = 3000;
    public static final int CRAWLING_OUTWARDS = 4000;
    
    private final ConcentrationSite concentration;
    private final CollectionSite collection;
    private final Museum museum;
    
    private AssaultParty party;
    
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
        
        this.position = 0;
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
     * Get the OrdinaryThief party
     * @return Party assigned to the OrdinaryThief.
     */
    public AssaultParty getParty()
    {
        return this.party;
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
     * Set the thief position
     * @param position New position
     */
    public void setPosition(int position)
    {
        this.position = position;
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
     * Get thief maximum displacement.
     * @return Thief maximum displacement.
     */
    public int getDisplacement()
    {
        return this.maximumDisplacement;
    }
    
    /**
     * Function called by the MasterThief to get the canvas from the OrdinaryThief directly.
     * @return True if there is a canvas to hand, false otherwise.
     */
    public boolean handCanvas()
    {
        boolean canvas = this.hasCanvas;
        this.hasCanvas = false;
        return canvas;
    }
    
    /**
     * Set thief state
     * @param state
     */
    private void setState(int state)
    {
        this.state = state;
        
        System.out.println("Thief " + this.id + " change state " + this.state);
    }
    
    /**
     * Prepare execution, assign party to thief and change state to crawling inwards and sets thief to sleep until the master thief or another thief wakes it up.
     * Enter the concentration site and wait until a party is assigned.
     * @throws java.lang.InterruptedException Exception     
     */
    private void prepareExecution() throws InterruptedException
    {
        this.concentration.enterAndWait(this);

        System.out.println("Thief " + this.id + " party assigned " + this.party.getID());
    }
    
    /**
     * Updates thief position inside the museum and set thief back to sleep, until another thief wakes it up.
     * @throws java.lang.InterruptedException Exception
     */
    private void crawlIn() throws InterruptedException
    {
        this.setState(CRAWLING_INWARDS);  
        while(this.party.crawlIn(this))
        {
            System.out.println("Thief " + this.id + " crawlIn (Position:" + this.position + ")");
        }
        
        System.out.println("Thief " + this.id + " reached room (Position:" + this.position + ")");
    }
    
    /**
     * Try to collect a canvas from the room and reverse direction after.
     */
    private void rollACanvas()
    {
        this.hasCanvas = this.museum.rollACanvas(this.party.getTarget());
        
        System.out.println("Thief " + this.id + " rollACanvas (HasCanvas:" + this.hasCanvas + ")");
    }
    
    /**
     * Change state to crawling outwards.
     */
    private void reverseDirection() throws InterruptedException
    {
        this.setState(AT_A_ROOM);        
        this.party.reverseDirection();
    }
    
    /**
     * Update position crawling out of the museum.
     * @throws java.lang.InterruptedException Exception
     */
    private void crawlOut() throws InterruptedException
    {
        this.setState(CRAWLING_OUTWARDS);
        while(this.party.crawlOut(this))
        {
            System.out.println("Thief " + this.id + " crawlOut (Position:" + this.position + ")");
        }
        
        System.out.println("Thief " + this.id + " reached outside (Position:" + this.position + ")");
    }
    
    /**
     * Hand the canvas (if there is one) to the master thief.
     */
    private void handACanvas() throws InterruptedException
    {
        System.out.println("Thief " + this.id + " handACanvas");
        this.collection.handACanvas(this);
    }
    
    /**
     * Check if the thief is still needed (check if every room has been emptied)
     */
    private boolean amINeeded()
    {
        System.out.println("Thief " + this.id + " amINeeded");
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
        catch(InterruptedException e)
        {
            System.out.println("Thief " + this.id + " error (" + e + ")");
        }
        
        System.out.println("Thief " + this.id + " terminated");
    }
}