package heist.room;

import java.io.Serializable;

/**
 * Class to represent the room as the MasterThief perceives it.
 * Stores how many paintings were retrieved from a room, if the room is cleared and if there is an AssaultParty currently attacking the room.
 */
public class RoomStatus extends Room implements Serializable
{
    private static final long serialVersionUID = 79438573L;
    
    /**
     * Flag to mark if the room has been cleared out.
     */
    private boolean clear;
    
    /**
     * Stores the number of thieves currently attacking (or on their way to attack) the room.
     * Its incremented with a thief enters a party that targets this room, and is decreased when the thieve returns from the room.
     */
    private int thievesAttacking;

    /**
     * RoomStatus constructor from id and distance.
     * Number of paintings always starts at 0.
     * @param id Room id.
     * @param distance Room distance.
     */
    public RoomStatus(int id, int distance)
    {
        super(id, distance, 0);
        
        this.clear = false;
        this.thievesAttacking = 0;
    }
    
    /**
     * Set if there is a party assigned to this room. Indication the number of thieves attacking the room.
     * @param thievesAttacking Number of thieves attacking the room.
     */
    public void addThievesAttacking(int thievesAttacking)
    {
        this.thievesAttacking += thievesAttacking;
    }
    
    /**
     * Get how many thieves are still attacking the room.
     * This number is decreased when returning from the room with a canvas or empty handed.
     * @return Number of thieves attacking the room.
     */
    public int getThievesAttacking()
    {
        return this.thievesAttacking;
    }
    
    /**
     * Check if there is a party attacking this room.
     * @return True if there is a party assigned to this room.
     */
    public boolean underAttack()
    {
        return this.thievesAttacking > 0;
    }
    
    /**
     * Check if the room is cleared out.
     * @return True if the room is cleared out.
     */
    public boolean isClear()
    {
        return this.clear;
    }

    /**
     * Mark the room as clear.
     * @throws java.lang.Exception Throws exception if thievesAttacking value goes bellow zero.
     */
    public void setClear() throws Exception
    {
        this.thievesAttacking--;
        this.clear = true;
        
        if(this.thievesAttacking < 0)
        {
            throw new Exception("Delivering more canvas than the ammout of thieves that attacked!");
        }
    }
    
    /**
     * Deliver painting to this room.
     * Adds a new painting to the room and reduces the number of thieves attacking the room value.
     * @throws java.lang.Exception Throws exception if thievesAttacking value goes bellow zero.
     */
    public void deliverPainting() throws Exception
    {
        this.thievesAttacking--;
        this.paintings++;
        
        if(this.thievesAttacking < 0)
        {
            throw new Exception("Delivering more canvas than the ammout of thieves that attacked!");
        }
    }
}