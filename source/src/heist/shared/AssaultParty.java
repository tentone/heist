package heist.shared;

import heist.queue.Queue;
import heist.thief.OrdinaryThief;
import java.util.Iterator;

/**
 * AssaultParty represents a group of OrdinaryThieves attacking the museum.
 * Its used as a synchronization point between thieves.
 * AsaultParties are dynamically created and destructed during the simulation
 * Assault party is shared between thieves.
 */
public class AssaultParty
{
    private static int IDCounter = 0;
    
    private final static int WAITING = 1000;
    private final static int CRAWLING_IN = 2000;
    private final static int CRAWLING_OUT = 3000;
    
    private final int id, size, maxDistance;
    private final Queue<OrdinaryThief> thieves, crawling;
    private int target, targetDistance;
    private int state;
    
    /**
     * AssaultParty constructor, assault parties are constructed by the MasterThief.
     * @param size Assault party size.
     * @param target Target room id.
     * @param targetDistance Target room distance.
     * @param maxDistance Maximum distance between thieves.
     */
    public AssaultParty(int size, int target, int targetDistance, int maxDistance)
    {
        this.id = IDCounter++;
        this.thieves = new Queue<>();
        this.crawling = new Queue<>();
        this.state = AssaultParty.WAITING;
        
        this.target = target;
        this.targetDistance = targetDistance;
        this.size = size;
        
        this.maxDistance = maxDistance;
    }
    
    /**
     * Get party id
     * @return Party ID
     */
    public synchronized int getID()
    {
        return this.id;
    }
    
    /**
     * Get assault party target room.
     * @return Target room.
     */
    public synchronized int getTarget()
    {
        return this.target;
    }
    
    /**
     * Get AssaultParty OrdinaryThieves iterator
     * @return Iterator.
     */
    public synchronized Iterator<OrdinaryThief> getThieves()
    {
        return this.thieves.iterator();
    }
    
    /**
     * Get assault party target room distance.
     * @return Target room distance.
     */
    public synchronized int getTargetDistance()
    {
        return this.targetDistance;
    }
    
    /**
     * Get assault party size.
     * @return Assault party size.
     */
    public synchronized int getSize()
    {
        return this.size;
    }
    
    /**
     * Add thief to party if the party is full the thief is not added.
     * @param thief Thief to be added to the party.
     */
    public synchronized void addThief(OrdinaryThief thief)
    {
        if(!this.partyFull())
        {
            thief.setParty(this);
            this.thieves.push(thief);
        }
    }
    
    /**
     * Called by the master thief to send the party to the museum.
     * Wakes up the first thief that is waiting to crawlIn.
     * @throws java.lang.InterruptedException Exception
     */
    public synchronized void sendParty() throws InterruptedException
    {
        this.state = AssaultParty.CRAWLING_IN;
        this.notify();
    }
    
    /**
     * Check if the party is full.
     * @return True if the party element queue has the same size as the party size.
     */
    public synchronized boolean partyFull()
    {
        return this.thieves.size() == this.size;
    }
    
    /**
     * Method used to update the position the thieves
     * @param thief Thief to update position of
     * @throws java.lang.InterruptedException Exception
     * @return True if was able to move the thief, false if thief arrived at the room
     */
    public synchronized boolean crawlIn(OrdinaryThief thief) throws InterruptedException
    {
        this.crawling.push(thief);
        
        while(this.crawling.peek() != thief)
        {
            this.wait();
        }
        
        boolean continueCrawling = true;
        int position = thief.getPosition() + thief.getDisplacement();
        
        Iterator<OrdinaryThief> it = this.thieves.iterator();
        while(it.hasNext())
        {
            OrdinaryThief t = it.next();
            if(t != thief)
            {
                if(position - t.getPosition() > this.maxDistance)
                {
                    position = t.getPosition() + this.maxDistance;
                }
            }
        }
        
        if(position > this.targetDistance)
        {
            position = this.targetDistance;
            continueCrawling = false;
        }
        else
        {
            while(this.isSomebodyAt(position) && position > thief.getPosition())
            {
                position--;
            }
        }
        
        thief.setPosition(position);
        this.crawling.pop();
        
        this.notify();
        
        return continueCrawling;
    }
    
    /**
     * Reverse assault party direction. Waits for all party members to arrive at the room and pick up a canvas.
     * The thieves wait for the last one to arrive. The last thieve to arrive wakes everybody.
     * @throws java.lang.InterruptedException Exception
     */
    public synchronized void reverseDirection() throws InterruptedException
    {
        if(!this.allThiefsAtState(OrdinaryThief.AT_A_ROOM))
        {
            this.wait();
        }
        
        this.notifyAll();
        
        this.state = AssaultParty.CRAWLING_OUT;
    }
    
    /**
     * Check if every OrdinaryThief in the party has the same state.
     * @param state State to be checked.
     * @return True if all thieves have the same indicated state, false otherwise.
     */
    private synchronized boolean allThiefsAtState(int state)
    {
        Iterator<OrdinaryThief> it = thieves.iterator();
        while(it.hasNext())
        {
            if(it.next().state() != state)
            {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Check if there is some Thief is at position
     * @return True if some thief is at that position
     */
    private synchronized boolean isSomebodyAt(int position)
    {
        Iterator<OrdinaryThief> it = thieves.iterator();
        while(it.hasNext())
        {
            if(it.next().getPosition() == position)
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Method used to update the position the thieves.
     * @param thief Thief to update position of.
     * @throws java.lang.InterruptedException Exception
     * @return True if was able to move the thief, false if thief arrived at the room.
     */
    public synchronized boolean crawlOut(OrdinaryThief thief) throws InterruptedException
    {
        this.crawling.push(thief);
        
        while(this.crawling.peek() != thief)
        {
            this.wait();
        }
        
        boolean continueCrawling = true;
        int position = thief.getPosition() - thief.getDisplacement();
        
        //TODO <DISTANCE BETWEEN THIEVES>
        
        if(position < 0)
        {
            position = 0;
            continueCrawling = false;
        }
        else
        {
            while(this.isSomebodyAt(position) && position < thief.getPosition())
            {
                position++;
            }
        }
        
        thief.setPosition(position);
        
        this.crawling.pop();
        this.notify();
        
        return continueCrawling;
    }
}
