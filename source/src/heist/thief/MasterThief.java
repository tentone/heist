package heist.thief;

import heist.Configuration;
import heist.Console;
import heist.GeneralRepository;
import heist.shared.AssaultParty;
import heist.shared.CollectionSite;
import heist.shared.ConcentrationSite;
import heist.shared.Museum;
import heist.shared.Room;
import java.util.Iterator;

/**
 * MasterThief is an active entity responsible from planning and prepare the Heist.
 * It creates parties of Thieves and sends them to get Paintings from the Museum.
 * The MasterThief does not know how many paintings exist inside each Room but knows how many rooms there are inside the Museum and where they are.
 */
public class MasterThief extends Thread
{
    public static final int PLANNING_THE_HEIST = 1000;
    public static final int DECIDING_WHAT_TO_DO = 2000;
    public static final int ASSEMBLING_A_GROUP = 3000;
    public static final int WAITING_FOR_GROUP_ARRIVAL = 4000;
    public static final int PRESENTING_THE_REPORT = 5000;

    private final CollectionSite collection;
    private final ConcentrationSite concentration;
    private final Museum museum;
    
    private final Configuration configuration;
    
    private int state;
    private RoomStatus[] rooms;
        
    /**
     * MasterThief constructor
     * @param repository GeneralRepository
     * @param configuration Simulation configuration
     */
    public MasterThief(GeneralRepository repository, Configuration configuration)
    {
        this.state = MasterThief.PLANNING_THE_HEIST;
        
        this.collection = repository.getCollectionSite();
        this.concentration = repository.getConcentrationSite();
        this.museum = repository.getMuseum();
        this.configuration = configuration;
        
        Room[] museum = this.museum.getRooms();
        this.rooms = new RoomStatus[museum.length];
        for(int i = 0; i < museum.length; i++)
        {
            this.rooms[i] = new RoomStatus(museum[i].getID(), museum[i].getDistance());
        }
    }
    
    /**
     * Checks if all rooms are clear.
     * Uses internal RoomStatus objects inside MasterThief object.
     * @return True if all rooms are clear.
     */
    private boolean allRoomsClear()
    {
        for(int i = 0; i < this.rooms.length; i++)
        {
            if(!this.rooms[i].isClear())
            {
                return false;
            }
        }
    
        return true;
    }
    
    /**
     * Updated the RoomStatus after receiving a painting from that room.
     * If an empty handed thief arrives marks the room as clean.
     * Used inside the collectionSite to transfer the canvas from the OrdinaryThief to the MasterThief.
     * @param id Room id.
     * @param canvas If true a canvas was delivered.
     */
    public void collectCanvas(int id, boolean canvas)
    {
        if(canvas)
        {
            this.rooms[id].addPainting();
        }
        else
        {
            this.rooms[id].setClear();
        }
    }
    
    /**
     * Get next room to assign to a party.
     * @return Next room to assign, null if all rooms have already been assigned.
     */
    private RoomStatus nextTargetRoom()
    {
        for(int i = 0; i < this.rooms.length; i++)
        {
            if(!this.rooms[i].isClear())
            {
                return this.rooms[i];
            }
        }
        
        return null;
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
            sum += this.rooms[i].getPaintings();
        }
        
        return sum;
    }
    
    /**
     * Change MasterThief state
     * @param state 
     */
    private void setState(int state)
    {
        this.state = state;

        Console.log("Master change state " + this.state);
    }
    
    /**
     * This is the first state change in the MasterThief life cycle it changes the MasterThief state to deciding what to do. 
     */
    private void startOperations()
    {
        this.setState(MasterThief.DECIDING_WHAT_TO_DO);
    }
    
    /**
     * Analyze the situation and take a decision.
     * Decision can be to create a new assault party, take a rest or to sum up results and end the heist.
     */
    private void appraiseSit()
    {
        Console.log("Master appraiseSit");
        
        if(this.allRoomsClear())
        {
            this.setState(MasterThief.PRESENTING_THE_REPORT);
        }
        else if(this.concentration.hasEnoughToCreateParty(this.configuration.partySize))
        {
            this.setState(MasterThief.ASSEMBLING_A_GROUP);
        }
        else
        {
            this.setState(MasterThief.WAITING_FOR_GROUP_ARRIVAL);
        }
    }
    
    /**
     * Assembly an assault party with thieves from the ConcentrationSite.
     * @return AssaultParty assembled.
     * @throws java.lang.InterruptedException Exception
     */
    private AssaultParty prepareAssaultParty() throws InterruptedException
    {
        RoomStatus room = this.nextTargetRoom();
        
        AssaultParty party = this.concentration.createNewParty(this.configuration.partySize, this.configuration.thiefDistance, room);
        
        String members = "";
        Iterator<OrdinaryThief> it = party.getThieves();
        while(it.hasNext())
        {
            members += it.next().getID();
            if(it.hasNext())
            {
                members += ", ";
            }
        }
        Console.log("Master prepareAssaultParty (ID:" + party.getID() + " Distance:" + party.getTargetDistance() + " Members:" + members + ")");
        
        return party;
    }
    
    /**
     * Send assault party with thieves from the party created.
     * Wakes up the first thief in the party. That thief will wake the other thieves.
     * @param party Party to send.
     * @throws java.lang.InterruptedException Exception
     */
    private void sendAssaultParty(AssaultParty party) throws InterruptedException
    {
        Console.log("Master sendAssaultParty " + party.getID());
        
        party.sendParty();
        this.setState(MasterThief.DECIDING_WHAT_TO_DO);
    }
    
    /**
     * The MasterThief waits in the CollectionSite until is awaken by an incoming OrdinaryThief.
     * @throws java.lang.InterruptedException Exception
     */
    private void takeARest() throws InterruptedException
    {
        Console.log("Master takeARest");
        
        this.collection.takeARest();
    }
    
    /**
     * Collect canvas from thieve waiting in the collection site.
     * Add canvas to the correspondent RoomStatus, if empty handed mark the room as clean.
     * @throws java.lang.InterruptedException Exception
     */
    private void collectCanvas() throws InterruptedException
    {
        this.collection.collectCanvas(this);

        Console.log("Master collectCanvas (Total:" + this.totalPaintingsStolen() + ")");
        
        this.setState(MasterThief.DECIDING_WHAT_TO_DO);
    }
    
    /**
     * Sum up the heist results, prepare a log of the heist and end the hole simulation.
     * Stop all thieves.
     */
    private void sumUpResults()
    {
        this.concentration.sumUpResults();
        
        Console.log(">>>>>>>>>>>>>>" + this.totalPaintingsStolen() + " paintings were stolen!<<<<<<<<<<<<<<<<<<<<<");
    }
    
    @Override
    public void run()
    {
        Console.log("Master started");
        
        try
        {
            this.startOperations();

            while(this.state != MasterThief.PRESENTING_THE_REPORT)
            {
                this.appraiseSit();

                if(this.state == MasterThief.WAITING_FOR_GROUP_ARRIVAL)
                {
                    this.takeARest();
                    this.collectCanvas();
                }
                else if(this.state == MasterThief.ASSEMBLING_A_GROUP)
                {
                    this.sendAssaultParty(this.prepareAssaultParty());
                }
            }
            
            this.sumUpResults();
        }
        catch(Exception e)
        {
            Console.log("Master error");
            e.printStackTrace();
        }

        
        Console.log("Master terminated");
    }
}
