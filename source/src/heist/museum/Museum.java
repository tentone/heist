package heist.museum;

/**
 * Museum has rooms inside of it, the OrdinaryThieves attack the Museum to stole the paintings hanging in those rooms.
 * Museum is shared memory region.
 * @author Jose Manuel
 */
public class Museum
{
    private static int IDCount = 0;
    
    private final Room[] rooms;
    private final int id;
    
    /**
     * Museum constructor
     * @param size Number of rooms inside the museum
     */
    public Museum(int size)
    {
        this.rooms = new Room[size];
        this.id = IDCount++;
    }
    
    /**
     * Get museum id.
     * @param id Museum id.
     */
    public synchronized int getID()
    {
        return this.id;
    }
    
    /**
     * Get room array
     * @return Array of Room objects
     */
    public synchronized Room[] getRooms()
    {
        return this.rooms;
    }
    
    /**
     * Get room from room position, returns null if there is no room at that position
     * @param position
     * @return Room object, null if not found
     */
    public synchronized Room getRoom(int position)
    {
        for(int i = 0; i < this.rooms.length; i++)
        {
            if(this.rooms[i].getPosition() == position)
            {
                return this.rooms[i];
            }
        }
        
        return null;
    }
}
