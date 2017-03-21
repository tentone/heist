package heist;

import heist.struct.Queue;
import heist.thief.OrdinaryThief;

//The concentration site is where thiefs wait for the master thief to assign them a party
public class ConcentrationSite
{
    private Queue<OrdinaryThief> queue;
    
    public ConcentrationSite()
    {   
        this.queue = new Queue<>();
    }
    
    //Add thief to the concentration site
    public void addThief(OrdinaryThief thief)
    {
        this.queue.push(thief);
    }
}
