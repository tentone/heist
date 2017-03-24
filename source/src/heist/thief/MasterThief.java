package heist.thief;

import heist.GeneralRepository;
import heist.shared.CollectionSite;
import heist.shared.ConcentrationSite;
import heist.shared.Room;

/**
 * MasterThief is an active entity responsible from planning and prepare the Heist.
 * It creates parties of Thieves and sends them to get Paintings from the Museum.
 * The MasterThief does not know how many paintings exist inside each Room but knows how many rooms there are inside the Museum and where they are.
 */
public class MasterThief extends Thread
{
    private static int IDCounter = 0;
    
    public static final int PLANNING_THE_HEIST = 1000;
    public static final int DECIDING_WHAT_TO_DO = 2000;
    public static final int ASSEMBLING_A_GROUP = 3000;
    public static final int WAITING_FOR_GROUP_ARRIVAL = 4000;
    public static final int PRESENTING_THE_REPORT = 5000;
            
    private int id, state;
    
    private CollectionSite collection;
    private ConcentrationSite concentration;
    private RoomStatus[] rooms;
    
    /**
     * MasterThief constructor
     * @param repository GeneralRepository
     */
    public MasterThief(GeneralRepository repository)
    {
        this.id = IDCounter++;
        this.collection = repository.getCollectionSite();
        this.concentration = repository.getConcentrationSite();
        this.state = PLANNING_THE_HEIST;
        
        Room[] museum = repository.getMuseum().getRooms();
        this.rooms = new RoomStatus[museum.length];
        for(int i = 0; i < museum.length; i++)
        {
            this.rooms[i] = new RoomStatus(museum[i].getID(), museum[i].getDistance());
        }
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
    public int state()
    {
        return this.state;
    }
    
    /**
     * Change MasterThief state
     * @param state 
     */
    private void setState(int state)
    {
        this.state = state;
    }
    
    /**
     * This is the first state change in the MasterThief life cycle it changes the MasterThief state to deciding what to do. 
     */
    private void startOperations()
    {
        this.setState(DECIDING_WHAT_TO_DO);
    }
    
    /**
     * Analyze the situation and take a decision.
     * Decision can be to create a new assault party, take a rest or to sum up results and end the heist.
     */
    private void appraiseSit()
    {
        if(false) //TODO <CHECK IF ALL ROOMS HAVE BEN EMPTIED>
        {
            this.setState(PRESENTING_THE_REPORT);
        }
        else if(this.concentration.hasEnoughToCreateParty(id))
        {
            this.setState(ASSEMBLING_A_GROUP);
        }
        else
        {
            this.setState(WAITING_FOR_GROUP_ARRIVAL);
        }
    }
    
    /**
     * Assembly an assault party with thieves from the ConcentrationSite.
     */
    private void prepareAssaultParty()
    {
        //TODO <ADD CODE HERE>
    }
    
    /**
     * Send assault party with thieves from the party created.
     * Wakes up the first thief in the party. That thief will wake the other thieves.
     */
    private void sendAssaultParty()
    {
        //TODO <ADD CODE HERE>
        
        this.setState(DECIDING_WHAT_TO_DO);
    }
    
    /**
     * Suspend the MasterThief activity until is awaken by another Thief.
     */
    private void takeARest() throws InterruptedException
    {
        this.collection.takeARest();
    }
    
    /**
     * Collect canvas from thieve waiting in the collection site.
     */
    private void collectCanvas()
    {
        //TODO <ADD CODE HERE>
        
        this.setState(DECIDING_WHAT_TO_DO);
    }
    
    /**
     * Sum up the heist results, prepare a log of the heist and end the hole simulation.
     * Stop all thieves.
     */
    private void sumUpResults()
    {
        //TODO <ADD CODE HERE>
    }
    
    @Override
    public void run()
    {
        System.out.println("MasterThief " + this.id + " started");
        
        try
        {
            this.startOperations();

            while(this.state != PRESENTING_THE_REPORT)
            {
                this.appraiseSit();

                if(this.state == WAITING_FOR_GROUP_ARRIVAL)
                {
                    this.takeARest();
                    this.collectCanvas();
                }
                else if(this.state == ASSEMBLING_A_GROUP)
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
    
    private class RoomStatus
    {
        final int distance, id;
        int paintings = 0;
        boolean clear = false;
        
        public RoomStatus(int id, int distance)
        {
            this.id = id;
            this.distance = distance;
        }
    }
}
