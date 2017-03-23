package heist.thief;

import heist.struct.Queue;

/**
 * AssaultParty represents a group of OrdinaryThieves attacking the museum.
 * Its used as a synchronization point between thieves.
 * AsaultParties are dynamically created and destructed during the simulation
 */
public class AssaultParty
{
    private static int IDCounter = 0;
    
    private final int id;
    private final int size, target;
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
     * Check if the party is full.
     * @return True if the party element queue has the same size as the party size.
     */
    public synchronized boolean partyFull()
    {
        return this.elements.size() == this.size;
    }
    
    /**
     * Move first element of the FIFO to the end of the FIFO.
     */
    public synchronized void moveFirstToEnd()
    {
        if(!this.elements.isEmpty())
        {
            this.elements.push(this.elements.pop());
        }
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
}
