package heist;

import heist.thief.OrdinaryThief;
import heist.utils.UUID;

public class Party
{
    private static int IDCounter = 0;
    
    private final int id;
    private int target;
    private OrdinaryThief[] elements;
    private int numberElements;

    public Party(int size, int target)
    {
        this.id = IDCounter++;
        this.target = target;
        this.elements = new OrdinaryThief[size];
        this.numberElements = 0;
    }
    
    //Check if the party is full
    public boolean partyFull()
    {
        return this.elements.length == this.numberElements;
    }
    
    //Get party id
    public synchronized int getID()
    {
        return this.id;
    }
    
    //Get party target room
    public synchronized int getTarget()
    {
        return this.target;
    }
    
    //Add thief to party
    public synchronized void addThief(OrdinaryThief thief)
    {
        thief.setParty(this.id);
        
        //TODO <ADD THIEF TO ELEMENTS ARRAY>
    }
}
