package heist.museum;

import heist.Configuration;

/**
 * Museum has rooms inside of it, the OrdinaryThieves attack the Museum to stole the paintings hanging in those rooms.
 * Museum is a shared region accessed by all thieves inside the museum.
 * @author Jose Manuel
 */
public class Museum
{
    private static int IDCount = 0;
    
    private final Room[] rooms;
    private final int id;
    
    /**
     * Museum constructor, initialises rooms with values from the configuration.
     * @param configuration Simulation configuration
     */
    public Museum(Configuration configuration)
    {
        this.id = IDCount++;
        
        this.rooms = new Room[configuration.numberRooms];
        for(int i = 0; i < this.rooms.length; i++)
        {
            boolean repeat = true;
            int distance = configuration.roomDistance.generateInRange();
            
            while(repeat)
            {
                repeat = false;

                for(int j = 0; j < i; j++)
                {
                    if(distance == this.rooms[j].getDistance())
                    {
                        distance = configuration.roomDistance.generateInRange();
                        repeat = true;
                        break;
                    }
                }
            }
            
            this.rooms[i] = new Room(i, distance, configuration.numberPaintings.generateInRange());
        }
    }
    
    /**
     * Get museum id.
     * @return Museum id.
     */
    public int getID()
    {
        return this.id;
    }
    
    /**
     * Get room array
     * @return Array of Room objects
     */
    public Room[] getRooms()
    {
        return this.rooms;
    }
    
    /**
     * Get room from room id, returns null if there is no room for that id.
     * @param id Room id
     * @return Room object, null if not found
     */
    public Room getRoom(int id)
    {
        if(id > 0 && id < this.rooms.length)
        {
            return this.rooms[id];
        }
        
        return null;
    }
    
    /**
     * Try to get painting from room using the room id
     * @param id Room id.
     * @return True if was able to get painting from room.
     */
    public synchronized boolean getPaiting(int id)
    {
        return this.getRoom(id).getPaiting();
    }
}
