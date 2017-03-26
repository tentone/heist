package heist.shared;

import heist.Room;
import heist.Configuration;

/**
 * Museum has rooms inside of it, the OrdinaryThieves attack the Museum to stole the paintings hanging in those rooms.
 * @author Jose Manuel
 */
public class Museum
{
    private final Room[] rooms;

    /**
     * Museum constructor, initializes rooms with values from the configuration.
     * @param configuration Simulation configuration
     */
    public Museum(Configuration configuration)
    {
        this.rooms = new Room[configuration.numberRooms];
        
        for(int i = 0; i < this.rooms.length; i++)
        {
            boolean repeat = true;
            int distance = configuration.roomDistance.generateInRange();
            int paintings = configuration.numberPaintings.generateInRange();
            
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
            
            this.rooms[i] = new Room(i, distance, paintings);
        }
        
        System.out.println("Museum has " + this.countPaintings() + " paintings!");
    }
    
    /**
     * Check how many paintings there are inside the museum.
     * @return Number of paintings inside the museum.
     */
    private synchronized int countPaintings()
    {
        int sum = 0;
        
        for(int i = 0; i < this.rooms.length; i++)
        {
            sum += this.rooms[i].getPaintings();
        }
        
        return sum;
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
     * Roll a canvas
     * @param id Room id
     * @return True if was able to get a canvas, false if the room was already empty
     */
    public synchronized boolean rollACanvas(int id)
    {
        return this.rooms[id].rollACanvas();
    }
}
