package heist;

public class Museum
{
    private static int IDCount = 0;
    
    private final Room[] rooms;
    
    
    public Museum(int size)
    {
        this.rooms = new Room[size];
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
