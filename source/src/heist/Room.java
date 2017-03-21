package heist;

public class Room
{
    private static int IDCounter = 0;
    
    private final int id;
    private final int position;
    private int paintings;

    public Room(int position, int paintings)
    {
        this.id = IDCounter++;
        this.position = position;
        this.paintings = paintings;
    }
    
    //Return room id
    public synchronized int getID()
    {
        return this.id;
    }
    
    //Return room position
    public synchronized int getPosition()
    {
        return this.position;
    }
    
    //True if room still has paintings
    public synchronized boolean hasPainting()
    {
        return this.paintings > 0;
    }
    
    //Remove a painting from the room
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
