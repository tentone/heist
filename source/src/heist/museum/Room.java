package heist.museum;

/**
 * Rooms contains painting that can be stolen by the Thieves attacking the museum.
 * @author Jose Manuel
 */
public class Room
{
    private final int id, distance;
    private int paintings;

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
     * Check is this rooms still has paintings.
     * @return True if there is paintings in the room.
     */
    public synchronized boolean hasPainting()
    {
        return this.paintings > 0;
    }
    
    /**
     * Remove a painting from the room.
     * @return True if the painting was removed.
     */
    public synchronized boolean getPaiting()
    {
        if(this.paintings > 0)
        {
            this.paintings--;
            return true;
        }
        
        return false;
    }
}
