package heist.concurrent.shared;

import heist.Configuration;
import heist.queue.ArrayQueue;
import heist.concurrent.thief.OrdinaryThief;
import heist.interfaces.AssaultParty;
import heist.room.RoomStatus;
import heist.queue.iterator.Iterator;
import heist.queue.Queue;

/**
 * AssaultParty represents a group of OrdinaryThieves attacking the museum.
 * Its used as a synchronisation point between thieves.
 * AsaultParties are dynamically created and destructed during the simulation
 * Assault party is shared between thieves.
 */
public class SharedAssaultParty implements AssaultParty
{
    /**
     * Waiting state.
     * When the party is in this state the OrdinaryThieves are joining or waiting for the MasterThieve to send them.
     */
    public static final int WAITING = 1000;
    
    /**
     * Crawling state.
     * When the party is in this state the OrdinaryThieves are crawling, or inside the room.
     */
    public static final int CRAWLING = 2000;
    
    /**
     * Dismissed state.
     * The party is set to this state after the last OrdinaryThieve delivers the canvas (or notifies the MasterThieve that he arrived empty handed).
     */
    public static final int DISMISSED = 3000;
    
    /**
     * AssaultParty id counter.
     */
    private static int IDCounter = 0;
    
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
     * @param configuration Simulation configuration
     */
    public SharedAssaultParty(Configuration configuration)
    {
        this.id = IDCounter++;
        
        this.thieves = new ArrayQueue<>(configuration.partySize);
        
        this.room = null;
        this.partySize = configuration.partySize;
        this.thiefDistance = configuration.thiefDistance;
        
        this.state = SharedAssaultParty.DISMISSED;
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
     * Get party state.
     * @return Party state.
     */
    public synchronized int getState()
    {
        return this.state;
    }
    
    /**
     * Get assault party target room.
     * @return Target room.
     */
    public synchronized int getTarget()
    {
        return this.room.getID();
    }
    
    /**
     * Reset party to its initial state.
     */
    private synchronized void reset()
    {
        this.state = SharedAssaultParty.DISMISSED;
        this.room = null;
        this.waitingToReverse = 0;
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
        return this.room.getDistance();
    }

    /**
     * Prepare party, set state to WAITING and set target room.
     * 
     * @param room TargetRoom.
     */
    public void prepareParty(RoomStatus room)
    {
        this.room = room;
        this.state = SharedAssaultParty.WAITING;
    }
    
    /**
     * Add thief to party if the party is full the thief is not added.
     * @param thief Thief to be added to the party.
     * @throws java.lang.Exception Throws exception if party is already full.
     */
    public synchronized void addThief(OrdinaryThief thief) throws Exception
    {
        if(!this.partyFull())
        {
            this.thieves.push(thief);
            thief.setParty(this);
        }
        else
        {
            throw new Exception("Party is full");
        }
    }
    
    /**
     * Remove thief from party.
     * After all thieves are removed from the party the party is dismissed.
     * @param thief Thief to be removed from party.
     */
    public synchronized void removeThief(OrdinaryThief thief)
    {
        this.thieves.remove(thief);
        
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
        this.state = SharedAssaultParty.CRAWLING;
        this.notifyAll();
    }
    
    /**
     * Check if the party is full.
     * @return True if the party element queue has the same size as the party size.
     */
    public synchronized boolean partyFull()
    {
        return this.partySize == this.thieves.size();
    }
    
    /**
     * Method used to update the position the thieves
     * @param thief Thief to update position of
     * @throws java.lang.InterruptedException Exception
     * @return True if was able to move the thief, false if thief arrived at the room
     */
    public synchronized boolean crawlIn(OrdinaryThief thief) throws InterruptedException
    {
        while(this.thieves.peek().getPosition() == this.room.getDistance())
        {
            this.thieves.popPush();
        }
        
        while(this.thieves.peek() != thief || this.state != SharedAssaultParty.CRAWLING)
        {
            this.wait();
            
            while(this.thieves.peek().getPosition() == this.room.getDistance())
            {
                this.thieves.popPush();
            }
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
        
        this.thieves.popPush();
        this.notifyAll();
        
        return thief.getPosition() != this.room.getDistance();
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
        while(this.thieves.peek().getPosition() == 0)
        {
            this.thieves.popPush();
        }
        
        while(this.thieves.peek() != thief)
        {
            this.wait();
            
            while(this.thieves.peek().getPosition() == 0)
            {
                this.thieves.popPush();
            }
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
        
        this.thieves.popPush();
        this.notifyAll();
        
        return thief.getPosition() != 0;
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
