package heist;

import heist.utils.UUID;

public class Museum
{
    private final Room[] rooms;
    private final String uuid;
    
    public Museum(int size)
    {
        this.rooms = new Room[size];
        this.uuid = UUID.generate();
    }
    
    public String getUUID()
    {
        return this.uuid;
    }
    
    public Room[] getRooms()
    {
        return this.rooms;
    }
    
    @Override
    public String toString()
    {
        return "Museum";
    }
}
