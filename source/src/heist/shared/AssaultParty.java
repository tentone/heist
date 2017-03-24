package heist.shared;

import heist.queue.Queue;
import heist.thief.OrdinaryThief;
import java.util.Iterator;

/**
 * AssaultParty represents a group of OrdinaryThieves attacking the museum.
 * Its used as a synchronization point between thieves.
 * AsaultParties are dynamically created and destructed during the simulation
 * Assault party is shared between thieves.
 */
public class AssaultParty
{
    private static int IDCounter = 0;
    
    private final int id, size, target;
    private final Queue<OrdinaryThief> elements;
    
    /**
     * AssaultParty constructor, assault parties are constructed by the MasterThief.
     * @param size Assault party size.
     * @param target Target room.
     */
    public AssaultParty(int size, int target)
    {
        this.id = IDCounter++;
        this.elements = new Queue<>();
        this.target = target;
        this.size = size;
    }
    
    /**
     * Get party id
     * @return Party ID
     */
    public int getID()
    {
        return this.id;
    }
    
    /**
     * Get assault party target room.
     * @return Target room.
     */
    public int getTarget()
    {
        return this.target;
    }

    /**
     * Get assault party size.
     * @return Assault party size.
     */
    public int getSize()
    {
        return this.size;
    }
    
    /**
     * Add thief to party if the party is full the thief is not added.
     * @param thief Thief to be added to the party.
     */
    public synchronized void addThief(OrdinaryThief thief)
    {
        if(!this.partyFull())
        {
            this.elements.push(thief);
        }
    }
    
    /**
     * Check if the party is full.
     * @return True if the party element queue has the same size as the party size.
     */
    public synchronized boolean partyFull()
    {
        return this.elements.size() == this.size;
    }
    
    /**
     * Reverse assault party direction. Waits for all party members to arrive at the room and pick up a canvas.
     * The thieves wait for the last one to arrive. The last thieve to arrive wakes everybody.
     * @throws java.lang.InterruptedException
     */
    public synchronized void reverseDirection() throws InterruptedException
    {
        while(!allThiefsAtARoom())
        {
            this.wait();
        }
        
        this.notifyAll();
    }
    
    /**
     * Check if every OrdinaryThief in the party state is AT_A_ROOM
     */
    private synchronized boolean allThiefsAtARoom()
    {
        Iterator<OrdinaryThief> it = elements.iterator();
        while(it.hasNext())
        {
            if(it.next().state() != OrdinaryThief.AT_A_ROOM)
            {
                return false;
            }
        }
        
        return true;
    }
}
