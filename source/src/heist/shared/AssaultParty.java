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
    
    private static int WAITING = 1000;
    private static int CRAWLING = 2000;
    
    private final int id, partySize, maxDistance;
    private final Queue<OrdinaryThief> thieves;
    private final Queue<OrdinaryThief> crawling;
    private int state;
    
    private int targetID, targetDistance;
    private int waitingToReverse;
    
    /**
     * AssaultParty constructor, assault parties are constructed by the MasterThief.
     * @param partySize Assault party size.
     * @param targetID Target room id.
     * @param targetDistance Target room distance.
     * @param maxDistance Maximum distance between thieves.
     */
    public AssaultParty(int partySize, int targetID, int targetDistance, int maxDistance)
    {
        this.id = IDCounter++;
        this.thieves = new Queue<>();
        this.crawling = new Queue<>();

        this.targetID = targetID;
        this.targetDistance = targetDistance;
        
        this.partySize = partySize;
        this.maxDistance = maxDistance;
        
        this.state = AssaultParty.WAITING;
        this.waitingToReverse = 0;
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
        return this.targetID;
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
        return this.partySize;
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
        this.state = AssaultParty.CRAWLING;
        this.notify();
    }
    
    /**
     * Check if the party is full.
     * @return True if the party element queue has the same size as the party size.
     */
    public synchronized boolean partyFull()
    {
        return this.thieves.size() == this.partySize;
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
        
        while(this.crawling.peek() != thief || this.state != AssaultParty.CRAWLING)
        {
            this.wait();
        }

        int position = thief.getPosition() + thief.getDisplacement();
        
        //TODO <LIMIT DISTANCE BETWEEN THIEVES>
        /*Iterator<OrdinaryThief> it = this.thieves.iterator();
        while(it.hasNext())
        {
            if()
            {
                
            }
        }*/
        
        if(position > this.targetDistance)
        {
            position = this.targetDistance;
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
        
        return position != this.targetDistance;
    }
    
    /**
     * Reverse assault party direction. Waits for all party members to arrive at the room and pick up a canvas.
     * The thieves wait for the last one to arrive. The last thieve to arrive wakes everybody.
     * @throws java.lang.InterruptedException Exception
     */
    public synchronized void reverseDirection() throws InterruptedException
    {
        this.waitingToReverse++;
        
        if(this.waitingToReverse != this.partySize)
        {
            while(this.waitingToReverse != 0)
            {
                this.wait();
            }
        }
        else
        {
            this.waitingToReverse = 0;
            this.notifyAll();
        }

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
        
        int position = thief.getPosition() - thief.getDisplacement();
        
        //TODO <LIMIT DISTANCE BETWEEN THIEVES>
        
        if(position < 0)
        {
            position = 0;
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
        
        return position != 0;
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
     * Check if all thieves are at a position.
     * @return False if some thief is not at position.
     */
    private synchronized boolean isEverybodyAt(int position)
    {
        Iterator<OrdinaryThief> it = thieves.iterator();
        while(it.hasNext())
        {
            if(it.next().getPosition() != position)
            {
                return false;
            }
        }
        
        return true;
    }
}
