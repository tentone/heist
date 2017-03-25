package heist.thief;

/**
 * Class to represent the room as the MasterThief perceives it.
 * Stores how many paintings were retrieved from a room, if the room is cleared and if there is an AssaultParty currently attacking the room.
 */
public class RoomStatus
{
    private final int distance, id;
    private int paintings;
    private boolean clear, assigned;

    public RoomStatus(int id, int distance)
    {
        this.id = id;
        this.distance = distance;
        this.paintings = 0;
        this.clear = false;
        this.assigned = false;
    }

    /**
     * Get room distance.
     * @return Distance.
     */
    public int getDistance()
    {
        return this.distance;
    }
    
    /**
     * Get room id.
     * @return ID.
     */
    public int getID()
    {
        return this.id;
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

    public void addPainting()
    {
        this.paintings++;
    }

    public int getPaintings()
    {
        return this.paintings;
    }
}