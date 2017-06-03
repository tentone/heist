package heist.concurrent.shared;

import heist.room.Room;
import heist.Configuration;
import heist.interfaces.Museum;
import java.io.Serializable;

/**
 * Museum has rooms inside of it, the OrdinaryThieves attack the Museum to stole the paintings hanging in those rooms.
 * @author Jose Manuel
 */
public class SharedMuseum implements Museum, Serializable
{
    private static final long serialVersionUID = 9932134632836L;
    
    /**
     * Rooms inside the museum.
     */
    private final Room[] rooms;

    /**
     * Museum constructor, initializes rooms with values from the configuration.
     * @param configuration Simulation configuration
     */
    public SharedMuseum(Configuration configuration)
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
        
        System.out.println("Info: Museum has " + this.countPaintings() + " paintings!");
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
     * Roll a canvas.
     * @param id Room id
     * @return True if was able to get a canvas, false if the room was already empty
     */
    public synchronized boolean rollACanvas(int id)
    {
        return this.rooms[id].rollACanvas();
    }
}
