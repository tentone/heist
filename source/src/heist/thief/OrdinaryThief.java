package heist.thief;

import heist.utils.UUID;

public class OrdinaryThief extends Thread
{
    private final String uuid;
    private OrdinaryThiefState state;
    private int position;
    
    public OrdinaryThief()
    {
        this.position = -1;
        this.uuid = UUID.generate();
        this.state = OrdinaryThiefState.OUTSIDE;
    }
    
    public OrdinaryThiefState state()
    {
        return this.state;
    }
    
    public int getPosition()
    {
        return this.position;
    }
    
    public String getUUID()
    {
        return this.uuid;
    }
    
    private void setState(OrdinaryThiefState state)
    {
        this.state = state;
    }
    
    private void prepareExecution()
    {
        //TODO <ADD CODE HERE>
    }
    
    private void crawlIn()
    {
        //TODO <ADD CODE HERE>
    }
    
    private void rollACanvas()
    {
        //TODO <ADD CODE HERE>
    }
    
    private void reverseDirection()
    {
        //TODO <ADD CODE HERE>
    }
    
    private void crawlOut()
    {
        //TODO <ADD CODE HERE>
    }
    
    @Override
    public void run()
    {
        System.out.println("Thief " + this.uuid + " started");
        
        while(true)
        {
            try
            {
                this.sleep(1000);
                System.out.println("Thief " + this.uuid + " updated");
            }
            catch(Exception e)
            {
                System.out.println("Thief " + this.uuid + " error");
                e.printStackTrace();
                break;
            }
        }
        
        System.out.println("Thief " + this.uuid + " terminated");
    }
}
