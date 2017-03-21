package heist;

import heist.utils.UUID;

public class Room
{
    private final String uuid, museum;
    private final int distance;
    private int paintings;

    public Room(String museum, int distance, int paintings)
    {
        this.uuid = UUID.generate();
        this.museum = museum;
        this.distance = distance;
        this.paintings = paintings;
    }
    
    public String getUUID()
    {
        return this.uuid;
    }
    
    public String getMuseum()
    {
        return this.museum;
    }
    
    public int getDistance()
    {
        return this.distance;
    }
    
    public synchronized boolean hasPainting()
    {
        return this.paintings > 0;
    }
    
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
