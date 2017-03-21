package heist;

import heist.thief.OrdinaryThief;
import heist.utils.UUID;
import java.util.ArrayList;

public class Party
{
    final private ArrayList<OrdinaryThief> elements;
    final private String uuid;
    final private int target;
    
    public Party(int target)
    {
        this.uuid = UUID.generate();
        this.elements = new ArrayList<>();
        this.target = target;
    }
    
    //Get party UUID
    public String getUUID()
    {
        return uuid;
    }
    
    //Add thief to party
    public synchronized void addThief(OrdinaryThief thief)
    {
        this.elements.add(thief);
    }
    
    //Remove thief from party
    public synchronized void removeThief(OrdinaryThief thief)
    {
        this.elements.remove(thief);
    }
}
