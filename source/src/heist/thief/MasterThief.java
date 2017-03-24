package heist.thief;

import heist.Configuration;
import heist.GeneralRepository;
import heist.shared.AssaultParty;
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
    private Configuration configuration;
    
    /**
     * MasterThief constructor
     * @param repository GeneralRepository
     */
    public MasterThief(GeneralRepository repository, Configuration configuration)
    {
        this.id = IDCounter++;
        this.state = PLANNING_THE_HEIST;
        
        this.collection = repository.getCollectionSite();
        this.concentration = repository.getConcentrationSite();
        this.configuration = configuration;
        
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
        if(this.allRoomsClear())
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
     * @return AssaultParty assembled.
     */
    private AssaultParty prepareAssaultParty()
    {
        int target = this.nextRoom();
        AssaultParty party = this.concentration.createNewParty(target, configuration.partySize);
        this.rooms[target].assign(party);
        
        return party;
    }
    
    /**
     * Send assault party with thieves from the party created.
     * Wakes up the first thief in the party. That thief will wake the other thieves.
     * @param party Party to send.
     */
    private void sendAssaultParty(AssaultParty party)
    {
        //Wakes up the first thief of the party
        party.sendParty();
        
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
        System.out.println(this.totalPaintingsStolen() + " paintings were stolen!");
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
                    AssaultParty party = this.prepareAssaultParty();
                    this.sendAssaultParty(party);
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
        public final int distance, id;
        public int paintings = 0;
        public boolean assigned = false, clear = false;
        public AssaultParty party;
        
        public RoomStatus(int id, int distance)
        {
            this.id = id;
            this.distance = distance;
        }
        
        public void assign(AssaultParty party)
        {
            this.party = party;
            this.assigned = true;
        }
        
        public void markClear()
        {
            this.clear = true;
        }
        
        public void addPainting()
        {
            this.paintings++;
        }
    }
    
    private RoomStatus[] rooms;
    
    /**
     * Checks if all rooms are clear.
     * Uses internal RoomStatus objects inside MasterThief object.
     * @return True if all rooms are clear.
     */
    private boolean allRoomsClear()
    {
        for(int i = 0; i < this.rooms.length; i++)
        {
            if(!this.rooms[i].clear)
            {
                return false;
            }
        }
    
        return true;
    }
    
    /**
     * Get next room to assign to a party.
     * @return ID of next room to assign, -1 if all rooms have been assigned.
     */
    private int nextRoom()
    {
        for(int i = 0; i < this.rooms.length; i++)
        {
            if(!this.rooms[i].assigned)
            {
                return this.rooms[i].id;
            }
        }
        
        return -1;
    }
    
    /**
     * Check how many paintings were stolen.
     * @return Number of paintings.
     */
    private int totalPaintingsStolen()
    {
        int sum = 0;
        
        for(int i = 0; i < this.rooms.length; i++)
        {
            sum += this.rooms[i].paintings;
        }
        
        return sum;
    }
}
