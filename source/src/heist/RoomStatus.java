package heist;

/**
 * Class to represent the room as the MasterThief perceives it.
 * Stores how many paintings were retrieved from a room, if the room is cleared and if there is an AssaultParty currently attacking the room.
 */
public class RoomStatus extends Room
{
    private boolean clear, assigned;

    /**
     * RoomStatus constructor from id and distance.
     * Number of paintings starts at 0.
     */
    public RoomStatus(int id, int distance)
    {
        super(id, distance, 0);
        
        this.clear = false;
        this.assigned = false;
    }
    
    /**
     * Set if there is a party assigned to this room.
     * @param assigned Assigned.
     */
    public void setAssigned(boolean assigned)
    {
        this.assigned = assigned;
    }

    /**
     * Check if there is a party assigned to this room.
     * @return True if there is a party assigned to this room.
     */
    public boolean isAssigned()
    {
        return this.assigned;
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
     */
    public void setClear()
    {
        this.clear = true;
    }
    
    /**
     * Add painting to room.
     */
    public void addPainting()
    {
        this.paintings++;
    }
}