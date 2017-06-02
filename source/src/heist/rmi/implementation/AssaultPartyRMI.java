package heist.rmi.implementation;

import heist.Configuration;
import heist.queue.ArrayQueue;
import heist.thief.OrdinaryThief;
import heist.interfaces.AssaultParty;
import heist.room.RoomStatus;
import heist.queue.iterator.Iterator;
import heist.queue.Queue;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * AssaultParty represents a group of OrdinaryThieves attacking the museum.
 * Its used as a synchronization point between thieves.
 * AsaultParties are dynamically created and destructed during the simulation
 * Assault party is shared between thieves.
 */
public class AssaultPartyRMI extends UnicastRemoteObject implements AssaultParty, Serializable
{
    private static final long serialVersionUID = 643825635992375L;
    
    /**
     * AssaultParty unique identifier.
     */
    private final int id;
    
    /**
     * Party size.
     */
    private final int partySize;
            
    /**
     * Maximum distance between crawling OrdinaryThieves.
     */
    private final int thiefDistance;
    
    /**
     * OrdinaryThieves in the party.
     */
    private final Queue<OrdinaryThief> thieves;

    /**
     * Crawling order
     */
    private final Queue<Integer> crawlingQueue;
    
    /**
     * Targeted room.
     */
    private RoomStatus room;
    
    /**
     * Number of OrdinaryThieves waiting to reverse direction.
     */
    private int waitingToReverse;
    
    /**
     * Party state.
     */
    private int state;
    
    /**
     * AssaultParty constructor, assault parties are constructed by the MasterThief.
     * @param id AssaultParty id.
     * @param configuration Simulation configuration
     */
    public AssaultPartyRMI(int id, Configuration configuration) throws RemoteException
    {
        super();
        
        this.id = id;
        
        this.thieves = new ArrayQueue<>(configuration.partySize);
        this.crawlingQueue = new ArrayQueue<>(configuration.partySize);
        
        this.room = null;
        this.partySize = configuration.partySize;
        this.thiefDistance = configuration.thiefDistance;
        
        this.state = AssaultPartyRMI.DISMISSED;
        this.waitingToReverse = 0;
    }
    
    /**
     * Get party id
     * @return Party ID
     */
    @Override
    public synchronized int getID()
    {
        return this.id;
    }
    
    /**
     * Get party state.
     * @return Party state.
     */
    @Override
    public synchronized int getState()
    {
        return this.state;
    }
    
    /**
     * Get assault party target room.
     * @return Target room ID, if no room is assigned to the party returns -1.
     */
    @Override
    public synchronized int getTarget()
    {
        if(this.room == null)
        {
            return -1;
        }
        
        return this.room.getID();
    }
    
    /**
     * Check if the party is full.
     * @return True if the party element queue has the same size as the party size.
     */
    @Override
    public synchronized boolean partyFull()
    {
        return this.partySize == this.thieves.size();
    }
    
    /**
     * Reset party to its initial state.
     */
    private synchronized void reset()
    {
        this.state = AssaultPartyRMI.DISMISSED;
        this.crawlingQueue.clear();
        this.thieves.clear();
        this.room = null;
        this.waitingToReverse = 0;
    }
    
    /**
     * Get thieves in this party.
     * @return Array of thieves.
     */
    @Override
    public synchronized int[] getThieves()
    {
        int[] thieves = new int[this.thieves.size()];
        
        Iterator<OrdinaryThief> it = this.thieves.iterator();
        for(int i = 0; i < thieves.length; i++)
        {
            thieves[i] = it.next().getID();
        }
        
        return thieves;
    }
    
    /**
     * Get assault party target room distance.
     * @return Target room distance.
     */
    public synchronized int getTargetDistance()
    {
        return this.room.getDistance();
    }

    /**
     * Prepare party, set state to WAITING and set target room.
     * @param room TargetRoom.
     */
    @Override
    public void prepareParty(RoomStatus room)
    {
        this.room = room;
        this.state = AssaultPartyRMI.WAITING;
    }
    
    /**
     * Add thief to party if the party is full the thief is not added.
     * @param thief Thief to be added to the party.
     * @throws java.lang.Exception Throws exception if party is already full.
     */
    @Override
    public synchronized void addThief(OrdinaryThief thief) throws Exception
    {
        if(!this.partyFull())
        {
            this.thieves.push(thief);
            this.crawlingQueue.push(thief.getID());
            thief.setParty(this.id);
        }
        else
        {
            throw new Exception("Party is full");
        }
    }
    
    /**
     * Remove thief from party.
     * After all thieves are removed from the party the party is dismissed.
     * @param id ID of the thief to be removed
     */
    public synchronized void removeThief(int id)
    {
        Iterator<OrdinaryThief> it = this.thieves.iterator();
        while(it.hasNext())
        {
            OrdinaryThief thief = it.next();
            if(thief.getID() == id)
            {
                this.thieves.remove(thief);
                break;
            }
        }
        
        if(this.thieves.isEmpty())
        {
            this.reset();
        }
    }
    
    /**
     * Called by the master thief to send the party to the museum.
     * Wakes up the first thief that is waiting to crawlIn.
     * @throws java.lang.InterruptedException Exception
     */
    @Override
    public synchronized void sendParty() throws InterruptedException
    {
        this.state = AssaultPartyRMI.CRAWLING;
        this.notifyAll();
    }
    
    /**
     * Method used to update the position the thieves
     * @param thief Thief to update position of
     * @throws Exception Exception
     * @return True if was able to move the thief, false if thief arrived at the room
     */
    public synchronized boolean crawlIn(OrdinaryThief thief) throws Exception
    {
        if(this.crawlingQueue.size() == 0)
        {
            throw new Exception("Crawling Queue size equals 0");
        }
        else if(this.crawlingQueue.peek() == null)
        {
            throw new Exception("Crawling peek returned null");
        }
        
        while(this.crawlingQueue.peek() != thief.getID() || this.state != AssaultPartyRMI.CRAWLING)
        {
            this.wait();
        }
        
        for(int delta = thief.getDisplacement(); delta > 0; delta--)
        {
            int position = thief.getPosition() + delta;
            boolean emptyPosition = true, distanceOk = true;
            
            //Check if position is valid
            Iterator<OrdinaryThief> ita = this.thieves.iterator();
            while(ita.hasNext())
            {
                OrdinaryThief ta = ita.next();
                
                //Collision
                if(ta != thief)
                {
                    if(ta.getPosition() == position)
                    {
                        emptyPosition = false;
                        break;
                    }
                }
                
                //Check distance
                Iterator<OrdinaryThief> itb = this.thieves.iterator();
                int minDistanceThief = Integer.MAX_VALUE;
                while(itb.hasNext())
                {
                    OrdinaryThief tb = itb.next();

                    //Distance
                    int distance = Math.abs(tb.getPosition() - ta.getPosition());
                    if(ta == thief)
                    {
                        distance = Math.abs(tb.getPosition() - position);
                    }

                    if(distance < minDistanceThief)
                    {
                        minDistanceThief = distance;
                    }
                }

                if(minDistanceThief > this.thiefDistance)
                {
                    distanceOk = false;
                    break;
                }
            }
            
            if(distanceOk)
            {
                if(position >= this.room.getDistance())
                {
                    thief.setPosition(this.room.getDistance());
                    break;
                }
                else if(emptyPosition)
                {
                    thief.setPosition(position);
                    break;
                }
            }
        }
        
        boolean keepCrawling = thief.getPosition() != this.room.getDistance();
        if(keepCrawling)
        {
            this.crawlingQueue.popPush();
        }
        else
        {
            this.crawlingQueue.pop();
        }
        
        this.notifyAll();

        return keepCrawling;
    }
    
    /**
     * Reverse assault party direction. Waits for all party members to arrive at the room and pick up a canvas.
     * The thieves wait for the last one to arrive. The last thieve to arrive wakes everybody.
     * @throws Exception Exception
     */
    public synchronized void reverseDirection(OrdinaryThief thief) throws Exception
    {
        this.waitingToReverse++;
        
        if(this.waitingToReverse < this.partySize)
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
        
        this.crawlingQueue.push(thief.getID());
        
        if(this.crawlingQueue.size() > this.partySize)
        {
            throw new Exception("CrawlingQueue bigger than partysize");
        }
    }
    
    /**
     * Method used to update the position the thieves.
     * @param thief Thief to update position of.
     * @throws Exception Exception
     * @return True if was able to move the thief, false if thief arrived at the room.
     */
    public synchronized boolean crawlOut(OrdinaryThief thief) throws Exception
    {
        if(this.crawlingQueue.size() == 0)
        {
            throw new Exception("Crawling Queue size equals 0");
        }
        else if(this.crawlingQueue.peek() == null)
        {
            throw new Exception("Crawling peek returned null");
        }
        
        while(this.crawlingQueue.peek() != thief.getID())
        {
            this.wait();
        }
        
        for(int delta = thief.getDisplacement(); delta > 0; delta--)
        {
            int position = thief.getPosition() - delta;
            boolean emptyPosition = true, distanceOk = true;
            
            //Check if position is valid
            Iterator<OrdinaryThief> ita = this.thieves.iterator();
            while(ita.hasNext())
            {
                OrdinaryThief ta = ita.next();
                
                //Collision
                if(ta != thief)
                {
                    if(ta.getPosition() == position)
                    {
                        emptyPosition = false;
                        break;
                    }
                }
                
                //Check distance between ta and all thieves
                Iterator<OrdinaryThief> itb = this.thieves.iterator();
                int minDistanceThief = Integer.MAX_VALUE;
                while(itb.hasNext())
                {
                    OrdinaryThief tb = itb.next();

                    //Distance
                    int distance = Math.abs(tb.getPosition() - ta.getPosition());
                    if(ta == thief)
                    {
                        distance = Math.abs(tb.getPosition() - position);
                    }

                    if(distance < minDistanceThief)
                    {
                        minDistanceThief = distance;
                    }
                }

                if(minDistanceThief > this.thiefDistance)
                {
                    distanceOk = false;
                    break;
                }
            }
            
            if(distanceOk)
            {
                if(position <= 0)
                {
                    thief.setPosition(0);
                    break;
                }
                else if(emptyPosition)
                {
                    thief.setPosition(position);
                    break;
                }
            }
        }

        boolean keepCrawling = thief.getPosition() != 0;
        if(keepCrawling)
        {
            this.crawlingQueue.popPush();
        }
        else
        {
            this.crawlingQueue.pop();
        }
        
        this.notifyAll();
        
        return keepCrawling;
    }
    
    /**
     * Compares two AssaultParties
     * @param object Object to be compared.
     * @return True if the assault parties have the same id.
     */
    @Override
    public boolean equals(Object object)
    {
        if(object instanceof AssaultPartyRMI)
        {
            AssaultPartyRMI party = (AssaultPartyRMI) object;
            return party.id == this.id;
        }
        return false;
    }
    
    /**
     * Generate string with AssaultParty members id.
     * @return String with AssaultParty information.
     */
    @Override
    public synchronized String toString()
    {
        String s = "[";
        Iterator<OrdinaryThief> it = this.thieves.iterator();
        while(it.hasNext())
        {
            s += it.next().getID();
            if(it.hasNext())
            {
                s += ", ";
            }
        }
        return s + "]";
    }
}
