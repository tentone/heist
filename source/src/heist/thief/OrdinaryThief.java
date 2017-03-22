package heist.thief;

/**
 * OrdinaryThief represents a thief active entity.
 * Thieves enter the museum stole canvas and come back to return it to the MasterThief.
 * Thieves attack in parties defined by the MasterThief and cannot move their comrades behind.
 * After taking a step the thief wakes up the last thief in the Party queue to make a step.
 */
public class OrdinaryThief extends Thread
{
    private static int IDCounter = 0;
    
    private OrdinaryThiefState state;
    private AssaultParty party;
    
    private final int id;
    private int position;
    private boolean hasCanvas = false;

    /**
     * OrdinaryThief constructor
     */
    public OrdinaryThief()
    {
        this.id = IDCounter++;
        this.state = OrdinaryThiefState.OUTSIDE;
        
        this.position = -1;
        this.hasCanvas = false;
        
        this.party = null;
    }
    
    /**
     * Get ordinary thief state
     * @return 
     */
    public OrdinaryThiefState state()
    {
        return this.state;
    }

    /**
     * Set party
     * @param party 
     */
    public synchronized void setParty(AssaultParty party)
    {
        this.party = party;
    }

    /**
     * Get the thief position
     * @return Thief position, -1 if outside
     */
    public synchronized int getPosition()
    {
        return this.position;
    }
    
    /**
     * Get thief ID
     * @return Thief ID
     */
    public synchronized int getID()
    {
        return this.id;
    }
    
    /**
     * Set thief state
     * @param state
     */
    private synchronized void setState(OrdinaryThiefState state)
    {
        this.state = state;
    }
    
    /**
     * Prepare execution, assign party to thief and change state to crawling inwards and sets thief to sleep until the master thief or another thief wakes it up.
     */
    private synchronized void prepareExecution() throws InterruptedException
    {
        this.setState(OrdinaryThiefState.CRAWLING_INWARDS);
        this.position = 0;
        
        this.wait();
    }
    
    /**
     * Updates thief position inside the museum and set thief back to sleep, until another thief wakes it up.
     */
    private synchronized void crawlIn()
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
    private synchronized void rollACanvas()
    {
        //TODO <ADD CODE HERE>
        
        this.reverseDirection();
    }
    
    /**
     * Change state to crawling outwards.
     */
    private synchronized void reverseDirection()
    {
        this.setState(OrdinaryThiefState.CRAWLING_OUTWARDS);
    }
    
    /**
     * Update position crawling out of the museum.
     */
    private synchronized void crawlOut()
    {
        //TODO <ADD CODE HERE>
    }
    
    /**
     * Hand the canvas (if there is one) to the master thief.
     */
    private synchronized void handACanvas()
    {
        //TODO <HAND THE CANVAS TO THE MASTER THIEF, ENTER THE COLLECTION SITE AND SLEEP>
    }
    
    /**
     * Check if the thief is still needed (check if every room has been emptied)
     */
    private synchronized boolean amINeeded()
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

/**
 * Represents OrdinaryThief state.
 */
enum OrdinaryThiefState
{
    OUTSIDE, CRAWLING_INWARDS, AT_A_ROOM, CRAWLING_OUTWARDS
}
