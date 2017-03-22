package heist.thief;

/**
 * OrdinaryThief represents a thief active entity.
 * Thieves enter the museum stole canvas and come back to return it to the MasterThief.
 * Thieves attack in parties defined by the MasterThief and cannot move their comrades behind.
 * After taking a step the thief wakes up the last thief in the Party queue to make a step.
 * @author Jose Manuel
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
     * Prepare execution, assign party to thief and change state to crawling inwards.
     */
    private synchronized void prepareExecution()
    {
        //TODO <ADD CODE HERE>
        
        this.setState(OrdinaryThiefState.CRAWLING_INWARDS);
    }
    
    /**
     * Update position inside the museum.
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
     * Thread run method
     */
    @Override
    public void run()
    {
        System.out.println("Thief " + this.id + " started");
        
        while(true)
        {
            try
            {
                if(this.state == OrdinaryThiefState.OUTSIDE)
                {
                    //handACanvas
                    //amINeeded

                    //prepareExecution
                }
                else if(this.state == OrdinaryThiefState.CRAWLING_INWARDS)
                {
                    //crawlIn
                }
                else if(this.state == OrdinaryThiefState.AT_A_ROOM)
                {
                    //rollACanvas

                    reverseDirection();
                }
                else if(this.state == OrdinaryThiefState.CRAWLING_OUTWARDS)
                {

                }
            }
            catch(Exception e)
            {
                System.out.println("Thief " + this.id + " error");
                e.printStackTrace();
                break;
            }
        }
        
        System.out.println("Thief " + this.id + " terminated");
    }
}

/**
 * Represents OrdinaryThief state.
 * @author Jose Manuel
 */
enum OrdinaryThiefState
{
    OUTSIDE, CRAWLING_INWARDS, AT_A_ROOM, CRAWLING_OUTWARDS
}
