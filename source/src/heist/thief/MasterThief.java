package heist.thief;

import heist.utils.UUID;

public class MasterThief extends Thread
{
    private final String uuid;
    private MasterThiefState state;
    
    public MasterThief()
    {
        this.uuid = UUID.generate();
        this.state = MasterThiefState.PLANNING_THE_HEIST;
    }
    
    public String getUUID()
    {
        return this.uuid;
    }
    
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
        System.out.println("MasterThief " + this.uuid + " started");
        
        while(true)
        {
            try
            {
                this.sleep(1000);
                System.out.println("MasterThief " + this.uuid + " updated");
            }
            catch(Exception e)
            {
                System.out.println("MasterThief " + this.uuid + " error");
                e.printStackTrace();
                break;
            }
        }
        
        System.out.println("MasterThief " + this.uuid + " terminated");
    }
}
