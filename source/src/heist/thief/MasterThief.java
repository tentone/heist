package heist.thief;

public class MasterThief extends Thread
{
    private static int IDCounter = 0;
    
    private int id;
    private MasterThiefState state;
    
    public MasterThief()
    {
        this.id = IDCounter++;
        this.state = MasterThiefState.PLANNING_THE_HEIST;
    }
    
    //Get id
    public int getID()
    {
        return this.id;
    }
    
    //Get Master thief state
    public MasterThiefState state()
    {
        return this.state;
    }
    
    private void setState(MasterThiefState state)
    {
        this.state = state;
    }
    
    private void appraiseSit()
    {
        //TODO <ADD CODE HERE>
    }

    private void startOperations()
    {
        //TODO <ADD CODE HERE>
    }
    
    private void sumUpResults()
    {
        //TODO <ADD CODE HERE>
    }
    
    private void collectCanvas()
    {
        //TODO <ADD CODE HERE>
    }
    
    private void takeARest()
    {
        //TODO <ADD CODE HERE>
    }
    
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
                System.out.println("MasterThief " + this.id + " updated");
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
