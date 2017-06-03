package heist.rmi.implementation;

import heist.Configuration;
import heist.interfaces.AssaultParty;
import heist.queue.ArrayQueue;
import heist.room.Room;
import heist.room.RoomStatus;
import heist.queue.Queue;
import heist.thief.MasterThief;
import heist.thief.OrdinaryThief;
import heist.interfaces.ControlCollectionSite;
import heist.interfaces.Museum;
import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;

/**
 * The ControlCollection site is where the OrdinaryThieves deliver the canvas to the MasterThief.
 * It is also responsible for decisions taken by booth the MasterThief and the OrdinaryThieves.
 * @author Jose Manuel
 */
public class ControlCollectionSiteRMI extends UnicastRemoteObject implements ControlCollectionSite, Serializable
{
    private static final long serialVersionUID = 457234977399923445L;
        
    /**
     * Configuration used by this ControlCollectionSite.
     */
    private final Configuration configuration;

    /**
     * Museum used to get information about rooms distance.
     */
    private final Museum museum;
    
    /**
     * RoomStatus array, used to store information about who are assaulting rooms, how many paintings where stolen from each room and if the room is empty.
     */
    private RoomStatus[] rooms;
    
    /**
     * AssaultParties used for the simulation.
     */
    private final AssaultParty[] parties;
    
    /**
     * Queue for OrdinaryThieves waiting to deliver a canvas.
     */
    private final Queue<OrdinaryThief> canvasDeliverQueue;
    
    /**
     * Queue for OrdinaryThieves waiting to check if they are still needed.
     * OrdinaryThieves are needed until the Museum is completely empty.
     */
    private final Queue<OrdinaryThief> amINeededQueue;
    
    /**
     * Flag to indicate if the heist has been terminated.
     * True when the MasterThieve decides that the Museum is clear.
     */
    private boolean heistTerminated;
    
    /**
     * Collection site constructor, creates a queue for OrdinaryThief.
     * @param parties AssaultParties
     * @param museum Museum
     * @param configuration Simulation configuration
     * @throws Exception Exception
     */
    public ControlCollectionSiteRMI(AssaultParty[] parties, Museum museum, Configuration configuration) throws Exception
    {   
        super();
        
        this.configuration = configuration;        
        this.parties = parties;
        this.museum = museum;
        
        this.canvasDeliverQueue = new ArrayQueue<>(configuration.numberThieves);
        this.amINeededQueue = new ArrayQueue<>(configuration.numberThieves);
        
        this.rooms = null;
        this.heistTerminated = false;
    }
    
    /**
     * Check if there is some thief waiting in the collection site.
     * @return True if there is some thief in the collection site.
     */
    public synchronized boolean hasThief()
    {
        return !this.canvasDeliverQueue.isEmpty();
    }
    
    /**
     * Get AssaultParties array.
     * @return Array of AssaultParties used.
     */
    public synchronized AssaultParty[] getParties()
    {
        return this.parties;
    }
    
    /**
     * Called by the master thief to verify where are the rooms inside the museum.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public synchronized void startOperations() throws Exception
    {
        Room[] museumRooms = this.museum.getRooms();
        this.rooms = new RoomStatus[museumRooms.length];
        for(int i = 0; i < museumRooms.length; i++)
        {
            this.rooms[i] = new RoomStatus(museumRooms[i].getID(), museumRooms[i].getDistance());
        }
    }
    
    /**
     * Check if there is some party available to be prepared and sent.
     * @return True if there is some party in DISMISSED state.
     * @throws Exception Exception
     */
    public synchronized boolean somePartyAvailable() throws Exception
    {
        for(int i = 0; i < this.parties.length; i++)
        {
            if(this.parties[i].getState() == AssaultPartyRMI.DISMISSED)
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Checks if all rooms are clear.
     * Uses internal RoomStatus objects inside MasterThief object.
     * @return True if all rooms are clear.
     */
    public synchronized boolean allRoomsClear()
    {
        for(int i = 0; i < this.rooms.length; i++)
        {
            if(!this.rooms[i].isClear() || this.rooms[i].underAttack())
            {
                return false;
            }
        }
    
        return true;
    }
    
    /**
     * Get next room to assign to a party.
     * @return Next room to assign, null if all rooms have already been assigned.
     */
    public synchronized RoomStatus nextTargetRoom()
    {
        for(int i = 0; i < this.rooms.length; i++)
        {
            if(!this.rooms[i].isClear() && !this.rooms[i].underAttack())
            {
                return this.rooms[i];
            }
        }
        
        return null;
    }
    
    /**
     * Check if there is any room under attack.
     * @return True if there is a room under attack.
     */
    private synchronized boolean thievesAttackingRooms()
    {
        for(int i = 0; i < this.rooms.length; i++)
        {
            if(this.rooms[i].underAttack())
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Check how many paintings were stolen.
     * @return Number of paintings.
     */
    public synchronized int totalPaintingsStolen()
    {
        int sum = 0;
        
        for(int i = 0; i < this.rooms.length; i++)
        {
            sum += this.rooms[i].getPaintings();
        }
        
        return sum;
    }
    
    /**
     * Check if there is conditions to create a new party.
     * @return True if there is conditions to create a new party.
     * @throws Exception Exception
     */
    public synchronized boolean canCreateParty() throws Exception
    {
        return this.nextTargetRoom() != null && this.amINeededQueue.size() >= this.configuration.partySize && this.somePartyAvailable();
    }
    
    /**
     * Check if the OrdinaryThief is still needed or if he can be terminated.
     * The OrdinaryThief blocks until prepareAssaultParty or sumUpResults is called by the MasterThief.
     * @param thief OrdinaryThief to check.
     * @throws java.lang.InterruptedException Exception
     * @return True if the OrdinaryThief is still needed
     */
    public synchronized boolean amINeeded(OrdinaryThief thief) throws InterruptedException
    {
        this.amINeededQueue.push(thief);
        this.notifyAll();
        
        do
        {
            this.wait();
        }
        while(this.amINeededQueue.contains(thief));
        
        return !this.heistTerminated;
    }
    
    /**
     * MasterThief call this function to prepare an assault party to be filled with OrdinaryThieves waiting in the concentration site.
     * @param room Target room.
     * @return AssaultParty created.
     * @throws Exception Exception
     */
    @Override
    public synchronized int prepareNewParty(RoomStatus room) throws Exception
    {
        for(int i = 0; i < this.parties.length; i++)
        {
            AssaultParty party = this.parties[i];
             
            if(party.getState() == AssaultPartyRMI.DISMISSED)
            {
                party.prepareParty(room);
                return party.getID();
            }
        }
        
        return -1;
    }
    
    /**
     * Analyse the situation and take a decision for the MasterThief.
     * Decision can be to create a new assault party, take a rest or to sum up results and end the heist.
     * @return New state to be attributed tho the MasterThief.
     * @throws Exception Exception
     */
    public synchronized int appraiseSit() throws Exception
    {
        if(this.allRoomsClear())
        {
            while(this.amINeededQueue.size() < this.configuration.numberThieves)
            {
                this.wait();
            }
            this.notifyAll();

            return MasterThief.PRESENTING_THE_REPORT;
        }
        else if(this.canCreateParty())
        {
            for(int i = 0; i < this.configuration.partySize; i++)
            {
                this.amINeededQueue.pop();
            }
            this.notifyAll();
            
            return MasterThief.ASSEMBLING_A_GROUP;
        }
        else if(this.thievesAttackingRooms())
        {
            return MasterThief.WAITING_FOR_GROUP_ARRIVAL;
        }

        this.wait();
        return MasterThief.DECIDING_WHAT_TO_DO;
    }
    
    /**
     * MasterThief gets a room for the OrdinaryThieves to attack.
     * The room is marked undeAttack by a partySize number of thieves.
     * @return Returns RoomStatus that describes the room to be attacked.
     */
    public synchronized RoomStatus getRoomToAttack()
    {
        RoomStatus room = this.nextTargetRoom();
        room.addThievesAttacking(configuration.partySize);
        
        return room;
    }
    
    /**
     * Function used to make MasterThief wait until a OrdinaryThief arrives and wakes him up.
     * @throws InterruptedException Exception
     */
    public synchronized void takeARest() throws InterruptedException
    {
        while(this.canvasDeliverQueue.isEmpty())
        {
            this.wait();
        }
    }
    
    /**
     * Add thief to the collection site and wake up the MasterThief to collect its canvas.
     * The OrdinaryThief enters the queue wakes up the MasterThief and waits until is waken up.
     * @param thief Thief to be added.
     * @throws java.lang.InterruptedException Exception.
     */
    public synchronized void handACanvas(OrdinaryThief thief) throws InterruptedException
    {
        this.canvasDeliverQueue.push(thief);
        this.notifyAll();
        
        while(this.canvasDeliverQueue.contains(thief))
        {    
            this.wait();
        }
    }
    
    /**
     * Function to allow the MasterThief to get the canvas bough by OrdinaryThieves.
     * The MasterThief wakes up the OrdinaryThieves waiting for their canvas to be collected.
     * Updates the RoomStatus after receiving a painting from that room.
     * If an empty handed thief arrives marks the room as clean.
     * @throws java.lang.InterruptedException Exception
     */
    public synchronized void collectCanvas() throws InterruptedException
    {
        if(!this.canvasDeliverQueue.isEmpty())
        {
            OrdinaryThief thief = this.canvasDeliverQueue.pop();
            AssaultParty party = this.parties[thief.getParty()];
            
            try
            {
                int targetID = party.getTarget();
                
                if(thief.deliverCanvas())
                {
                    this.rooms[targetID].deliverPainting();
                }
                else
                {
                    this.rooms[targetID].setClear();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
            this.notifyAll();
        }
    }
    
    /**
     * Called by the MasterThief to indicate that the heist is over.
     */
    public synchronized void sumUpResults()
    {
        this.heistTerminated = true;
        this.amINeededQueue.clear();
        this.notifyAll();
    }
}
