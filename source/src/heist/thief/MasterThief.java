package heist.thief;

/**
 * MasterThief is an active entity responsible from planning and prepare the Heist.
 * It creates parties of Thieves and sends them to get Paintings from the Museum.
 * The MasterThief does not know how many paintings exist inside each Room but knows how many rooms there are inside the Museum and where they are.
 */
public class MasterThief extends Thread
{
    private static int IDCounter = 0;
    
    private int id;
    private MasterThiefState state;
    
    /**
     * MasterThief constructor
     */
    public MasterThief()
    {
        this.id = IDCounter++;
        this.state = MasterThiefState.PLANNING_THE_HEIST;
    }
    
    /**
     * Get ID
     * @return MasterThief id 
     */
    public int getID()
    {
        return this.id;
    }
    
    /**
     * Get Master thief state
     * @return MasterThief state
     */
    public MasterThiefState state()
    {
        return this.state;
    }
    
    /**
     * Change MasterThief state
     * @param state 
     */
    private void setState(MasterThiefState state)
    {
        this.state = state;
    }
    
    /**
     * Analyse the situation and take a decision.
     * Decision can be to create a new assault party, take a rest or to sum up results and end the heist.
     */
    private synchronized void appraiseSit()
    {
        //TODO <ADD CODE HERE>
    }
    
    /**
     * This is the first state change in the MasterThief life cycle it changes the MasterThief state to deciding what to do. 
     */
    private synchronized void startOperations()
    {
        this.setState(MasterThiefState.DECIDING_WHAT_TO_DO);
    }
    
    /**
     * Sum up the heist results, prepare a log of the heist and end the hole simulation.
     * Stop all thieves.
     */
    private synchronized void sumUpResults()
    {
        //TODO <ADD CODE HERE>
    }
    
    /**
     * Collect canvas from thieve waiting in the collection site.
     */
    private synchronized void collectCanvas()
    {
        //TODO <ADD CODE HERE>
        
        this.setState(MasterThiefState.DECIDING_WHAT_TO_DO);
    }
    
    /**
     * Suspend the MasterThief activity until is awaken by another Thief.
     */
    private synchronized void takeARest() throws InterruptedException
    {
        this.wait();
    }
    
    /**
     * Assembly an assault party with thieves from the ConcentrationSite.
     */
    private synchronized void prepareAssaultParty()
    {
        //TODO <ADD CODE HERE>
    }
    
    /**
     * Send assault party with thieves from the party created.
     * Wakes up the first thief in the party. That thief will wake the other thieves.
     */
    private synchronized void sendAssaultParty()
    {
        //TODO <ADD CODE HERE>
        
        this.setState(MasterThiefState.DECIDING_WHAT_TO_DO);
    }
    
    @Override
    public void run()
    {
        System.out.println("MasterThief " + this.id + " started");
        
        try
        {
            this.startOperations();

            while(this.state != MasterThiefState.PRESENTING_THE_REPORT)
            {
                this.appraiseSit();

                if(this.state == MasterThiefState.WAITING_FOR_GROUP_ARRIVAL)
                {
                    this.takeARest();
                    this.collectCanvas();
                }
                else if(this.state == MasterThiefState.ASSEMBLING_A_GROUP)
                {
                    this.prepareAssaultParty();
                    this.sendAssaultParty();
                }
            }
            
            this.sumUpResults();
        }
        catch(Exception e)
        {
            System.out.println("MasterThief " + this.id + " error");
            e.printStackTrace();
        }

        
        System.out.println("MasterThief " + this.id + " terminated");
    }
}

/**
 * Represents a MasterThief state.
 */
enum MasterThiefState
{
    PLANNING_THE_HEIST, DECIDING_WHAT_TO_DO, ASSEMBLING_A_GROUP, WAITING_FOR_GROUP_ARRIVAL, PRESENTING_THE_REPORT
}
