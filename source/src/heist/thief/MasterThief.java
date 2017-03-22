package heist.thief;

/**
 * MasterThief is an active entity responsible from planning and prepare the Heist.
 * It creates parties of Thieves and sends them to get Paintings from the Museum.
 * The MasterThief does not know how many paintings exist inside each Room but knows how many rooms there are inside the Museum and where they are.
 * @author Jose Manuel
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
    private void appraiseSit()
    {
        //TODO <ADD CODE HERE>
    }
    
    /**
     * This is the first state change in the MasterThief life cycle it changes the MasterThief state to deciding what to do. 
     */
    private void startOperations()
    {
        //TODO <ADD CODE HERE>
        this.setState(MasterThiefState.DECIDING_WHAT_TO_DO);
    }
    
    /**
     * Sum up the heist results, prepare a log of the heist and end the hole simulation.
     * Stop all thieves.
     */
    private void sumUpResults()
    {
        //TODO <ADD CODE HERE>
    }
    
    /**
     * Collect canvas from thieve waiting in the collection site.
     */
    private void collectCanvas()
    {
        //TODO <ADD CODE HERE>
    }
    
    /**
     * Suspend the MasterThief activity until is awaken by another Thief.
     */
    private void takeARest()
    {
        //TODO <ADD CODE HERE>
    }
    
    /**
     * Assembly and send assault party with thieves from the ConcentrationSite
     */
    private void sendAssaultParty()
    {
        //TODO <ADD CODE HERE>
    }
    
    @Override
    public void run()
    {
        System.out.println("MasterThief " + this.id + " started");
        
        while(true)
        {
            try
            {

            }
            catch(Exception e)
            {
                System.out.println("MasterThief " + this.id + " error");
                e.printStackTrace();
                break;
            }
        }
        
        System.out.println("MasterThief " + this.id + " terminated");
    }
}

/**
 * Represents a MasterThief state.
 * @author Jose Manuel
 */
enum MasterThiefState
{
    PLANNING_THE_HEIST, DECIDING_WHAT_TO_DO, ASSEMBLING_A_GROUP, WAITING_FOR_GROUP_ARRIVAL, PRESENTING_THE_REPORT
}
