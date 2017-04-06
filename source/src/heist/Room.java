package heist;

/**
 * Rooms contains painting that can be stolen by the Thieves attacking the museum.
 * Room is a shared region accessed by all thieves inside the museum.
 * @author Jose Manuel
 */
public class Room
{
    /**
     * Room identification.
     */
    private final int id;
    
    /**
     * Room distance inside the museum.
     */
    private final int distance;
    
    /**
     * Number of paintings currently inside the room.
     * This value is decreased every time a thief takes a painting from the room.
     */
    protected int paintings;

    /**
     * Room constructor, the room stores its own position and the amount of paintings inside of it.
     * @param id Room id.
     * @param distance Room distance.
     * @param paintings Amount of paintings inside the room.
     */
    public Room(int id, int distance, int paintings)
    {
        this.id = id;
        this.distance = distance;
        this.paintings = paintings;
    }
    
    /**
     * Get room ID.
     * @return Room id.
     */
    public int getID()
    {
        return this.id;
    }
    
    /**
     * Get room position inside the museum.
     * @return Room position.
     */
    public int getDistance()
    {
        return this.distance;
    }
    
    /**
     * Get room paintings.
     * @return Room paintings.
     */
    public int getPaintings()
    {
        return this.paintings;
    }
    
    /**
     * Remove a painting from the room.
     * @return True if the painting was removed.
     */
    public boolean rollACanvas()
    {
        if(this.paintings > 0)
        {
            this.paintings--;
            return true;
        }
        
        return false;
    }
}
