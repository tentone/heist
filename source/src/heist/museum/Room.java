package heist.museum;

/**
 * Rooms contains painting that can be stolen by the Thieves attacking the museum.
 * Room is a shared region accessed by all thieves inside the museum.
 * @author Jose Manuel
 */
public class Room
{
    private static int IDCounter = 0;
    
    private final int id;
    private final int position;
    private int paintings;

    /**
     * Room constructor, the room stores its own position and the amount of paintings inside of it.
     * @param position Room position.
     * @param paintings Amount of paintings inside the room.
     */
    public Room(int position, int paintings)
    {
        this.id = IDCounter++;
        this.position = position;
        this.paintings = paintings;
    }
    
    /**
     * Get room ID.
     * @return Room id.
     */
    public synchronized int getID()
    {
        return this.id;
    }
    
    /**
     * Get room position inside the museum.
     * @return Room position.
     */
    public synchronized int getPosition()
    {
        return this.position;
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
