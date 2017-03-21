package heist.thief;

public class OrdinaryThief extends Thread
{
    private static int IDCounter = 0;
    
    private OrdinaryThiefState state;
    private int party;
    
    private final int id;
    private int position;
    private boolean hasCanvas = false;

    public OrdinaryThief()
    {
        this.position = -1;
        this.id = IDCounter++;
        this.state = OrdinaryThiefState.OUTSIDE;
        
        this.party = -1;
        this.hasCanvas = false;
    }
    
    //Get ordinarty thief state
    public OrdinaryThiefState state()
    {
        return this.state;
    }

    //Set party (by its id)
    public void setParty(int party)
    {
        this.party = party;
    }
    
    //Get thief position (-1 if its outside the museum)
    public int getPosition()
    {
        return this.position;
    }
    
    //Get thief if
    public synchronized int getID()
    {
        return this.id;
    }
    
    //Set thief state
    private void setState(OrdinaryThiefState state)
    {
        this.state = state;
    }
    
    //Prepare execution, assign party to thief and change state to crawlinginwars
    private synchronized void prepareExecution()
    {
        //TODO <ADD CODE HERE>
        
        this.setState(OrdinaryThiefState.CRAWLING_INWARDS);
    }
    
    //Update position inside the museum
    private synchronized void crawlIn()
    {
        //TODO <ADD CODE HERE>
        
        //if(this.position == this.party.getTarget())
        //{
        //    this.rollACanvas();
        //}
    }
    
    //Try to collect a canvas from the room
    private synchronized void rollACanvas()
    {
        //TODO <ADD CODE HERE>
    }
    
    //Change state to crawling outwards
    private synchronized void reverseDirection()
    {
        this.setState(OrdinaryThiefState.CRAWLING_OUTWARDS);
    }
    
    //Update position
    private synchronized void crawlOut()
    {
        //TODO <ADD CODE HERE>
    }
    
    //Thread runs at fixed rate defined by the period variable, and calls the update method
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
