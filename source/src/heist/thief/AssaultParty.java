package heist.thief;

import heist.struct.Queue;
import heist.thief.OrdinaryThief;

/**
 * AssaultParty represents a group of OrdinaryThieves attacking the museum.
 * Its used as a synchronisation point between thieves.
 * AsaultParties are dynamically created and destructed during the simulation
 * @author Jose Manuel
 */
public class AssaultParty
{
    private static int IDCounter = 0;
    
    private final int id;
    private final int size, target;
    private Queue<OrdinaryThief> elements;
    
    /**
     * AssaultParty constructor, assault parties are constructed by the MasterThief
     * @param size
     * @param target 
     */
    public AssaultParty(int size, int target)
    {
        this.id = IDCounter++;
        this.target = target;
        this.elements = new Queue<>();
        this.size = size;
    }
    
    /**
     * Check if the party is full.
     * @return True if the party element queue has the same size as the party size
     */
    public boolean partyFull()
    {
        return this.elements.size() == this.size;
    }
    
    /**
     * Get party id
     */
    public synchronized int getID()
    {
        return this.id;
    }
    
    /**
     * Get assault party target room
     */
    public synchronized int getTarget()
    {
        return this.target;
    }
    
    /**
     * Add thief to party if the party is full the thief is not added
     */
    public synchronized void addThief(OrdinaryThief thief)
    {
        if(!this.partyFull())
        {
            this.elements.push(thief);
        }
    }
}
