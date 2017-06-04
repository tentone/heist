package heist.thief;

import heist.Configuration;
import heist.interfaces.AssaultParty;
import heist.interfaces.ConcentrationSite;
import heist.interfaces.ControlCollectionSite;
import heist.interfaces.Logger;
import heist.interfaces.Museum;
import heist.utils.VectorialClock;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * OrdinaryThief represents a thief active entity.
 * Thieves enter the museum stole canvas and come back to return it to the MasterThief.
 * Thieves attack in parties defined by the MasterThief and cannot move their comrades behind.
 * After taking a step the thief wakes up the last thief in the Party queue to make a step.
 */
public class OrdinaryThief extends Thread implements Serializable
{
    private static final long serialVersionUID = 923475271232000L;
    
    /**
     * Outside state
     * When the thief is outside it is waiting for instructions provided by the master thief
     * It can join an AssaultParty and start crawling inside the museum or terminate
     */
    public static final int OUTSIDE = 1000;
    
    /**
     * Crawling inwards state
     */
    public static final int CRAWLING_INWARDS = 2000;
    
    /**
     * At a room state
     */
    public static final int AT_A_ROOM = 3000;
    
    /**
     * Crawling outwards
     */
    public static final int CRAWLING_OUTWARDS = 4000;
    
    /**
     * Concentration site
     */
    private final ConcentrationSite concentration;
    
    /**
     * Collection site
     */
    private final ControlCollectionSite controlCollection;
    
    /**
     * Museum
     */
    private final Museum museum;
    
    /**
     * Logger
     */
    private final Logger logger;
    
    /**
     * AssaultParties
     */
    private final AssaultParty[] parties;
    
    /**
     * Thief unique id.
     */
    private int id;
    
    /**
     * How far can the thief move in one step.
     */
    private int maximumDisplacement;
    
    /**
     * Party id
     */
    private int party;
    
    /**
     * Thief state.
     */
    private int state;
    
    /**
     * Thief crawling position.
     */
    private int position;
    
    /**
     * True if the thief carries a canvas.
     */
    private boolean hasCanvas;
    
    /**
     * Vectorial clock.
     */
    private VectorialClock clock;
    
    /**
     * OrdinaryThief constructor.
     * @param id Thief id.
     * @param controlCollection ControlCollectionSite
     * @param concentration ConcentrationSite
     * @param museum Museum
     * @param parties AssaultParties
     * @param logger Logger
     * @param configuration Simulation configuration
     */
    public OrdinaryThief(int id, ControlCollectionSite controlCollection, ConcentrationSite concentration, Museum museum, AssaultParty[] parties, Logger logger, Configuration configuration)
    {
        this.id = id;
        this.state = OrdinaryThief.OUTSIDE;
        
        this.controlCollection = controlCollection;
        this.concentration = concentration;
        this.museum = museum;
        this.parties = parties;
        this.logger = logger;
        
        this.clock = new VectorialClock();
        
        this.maximumDisplacement = configuration.thiefDisplacement.generateInRange();
        this.position = 0;
        this.hasCanvas = false;
        this.party = -1;
    }
    
    /**
     * Get ordinary thief state
     * @return Thief state.
     */
    public int state()
    {
        return this.state;
    }

    /**
     * Check if OrdinaryThief has a party.
     * @return Returns a P if has party or a W otherwise.
     */
    public char hasParty()
    {
        if(this.party == -1)
        {
            return 'W';
        }
        
        return 'P';
    }
    
    /**
     * Get the OrdinaryThief party
     * @return Party assigned to the OrdinaryThief.
     */
    public int getParty()
    {
        return this.party;
    }
    
    /**
     * Set assault party.
     * @param party Assault party.
     */
    public void setParty(int party)
    {
        this.party = party;
    }
    
    /**
     * Leave party is called after handing the canvas to the MasterThief.
     * @throws Exception An exception may be thrown.
     */
    public void leaveParty() throws Exception
    {
        if(this.party != -1)
        {
            this.clock.increment();
            this.parties[this.party].removeThief(this.id);
            this.party = -1;
        }
    }
    
    /**
     * Get the thief position
     * @return Thief position
     */
    public int getPosition()
    {
        return this.position;
    }
    
    /**
     * Set the thief position
     * @param position New position
     */
    public void setPosition(int position)
    {
        this.position = position;
    }
    
    /**
     * Get thief ID
     * @return Thief ID
     */
    public int getID()
    {
        return this.id;
    }

    /**
     * Update time on vectorial clock
     */
    public void updateClock()
    {
        this.clock.increment();
    }
    
    /**
     * Get timestamp from vectorialClock
     * @return Vectorial clock time
     */
    public int getTime()
    {
        return this.clock.getTime();
    }
    
    /**
     * Check if thieve has a canvas.
     * @return Return 1 if thief has a canvas 0 otherwise.
     */
    public int hasCanvas()
    {
        return this.hasCanvas ? 1 : 0;
    }
    
    /**
     * Get thief maximum displacement.
     * @return Thief maximum displacement.
     */
    public int getDisplacement()
    {
        return this.maximumDisplacement;
    }
    
    /**
     * Function called by the MasterThief to get the canvas from the OrdinaryThief directly.
     * @return True if there is a canvas to hand, false otherwise.
     */
    public boolean deliverCanvas()
    {
        boolean canvas = this.hasCanvas;
        
        this.hasCanvas = false;
        
        return canvas;
    }
    
    /**
     * Change OrdinaryThief state.
     * @param state New state.
     */
    private void setState(int state)
    {
        this.state = state; 
        //this.logger.debug("Thief " + this.id + " change state " + this.state);
    }
    
    /**
     * Check if the thief is still needed.
     * @throws java.lang.InterruptedException Exception   
     */
    private boolean amINeeded() throws Exception
    {
        this.clock.increment();
        boolean amINeeded = this.controlCollection.amINeeded(this);
        if(amINeeded)
        {
            this.logger.log(this);
        }
        
        //this.logger.debug("Thief " + this.id + " amINeeded " + amINeeded);
        
        return amINeeded;
    }
    
    /**
     * Prepare execution, assign party to thief and change state to crawling inwards and sets thief to sleep until the master thief or another thief wakes it up.
     * Enter the concentration site and wait until a party is assigned.
     * @throws Exception Exception     
     */
    private void prepareExecution() throws Exception
    {
        //this.logger.debug("Thief " + this.id + " entered the concentration site");
        
        this.clock.increment();
        this.setParty(this.concentration.prepareExcursion(this));
        this.logger.log(this);
        
        //this.logger.debug("Thief " + this.id + " party assigned " + this.party.getID());
    }
    
    /**
     * Updates thief position inside the museum and set thief back to sleep, until another thief wakes it up.
     * @throws java.lang.InterruptedException Exception
     */
    private void crawlIn() throws Exception
    {
        this.setState(OrdinaryThief.CRAWLING_INWARDS);

        while(this.parties[this.party].keepCrawling(this))
        {
            this.clock.increment();
            this.position = this.parties[this.party].crawlIn(this);
            this.logger.log(this);
            
            //this.logger.debug("Thief " + this.id + " crawlIn (Position:" + this.position + ")");
        }
        
        //this.logger.debug("Thief " + this.id + " reached room (Position:" + this.position + ")");
    }
    
    /**
     * Try to collect a canvas from the room and reverse direction after.
     */
    private void rollACanvas() throws Exception
    {
        this.setState(OrdinaryThief.AT_A_ROOM);
        
        this.clock.increment();
        int target = this.parties[this.party].getTarget();
        this.logger.log(this);
        
        this.clock.increment();
        this.hasCanvas = this.museum.rollACanvas(target);
        this.logger.log(this);
        
        //this.logger.debug("Thief " + this.id + " rollACanvas (HasCanvas:" + this.hasCanvas + ")");
    }
    
    /**
     * Change state to crawling outwards.
     */
    private void reverseDirection() throws Exception
    {      
        this.clock.increment();
        this.parties[this.party].reverseDirection(this);
        this.logger.log(this);
        
        //this.logger.debug("Thief " + this.id + " reverse");
    }
    
    /**
     * Update position crawling out of the museum.
     * @throws java.lang.InterruptedException Exception
     */
    private void crawlOut() throws Exception
    {
        this.setState(OrdinaryThief.CRAWLING_OUTWARDS);
        
        while(this.parties[this.party].keepCrawling(this))
        {
            this.clock.increment();
            this.position = this.parties[this.party].crawlOut(this);
            this.logger.log(this);
            
            //this.logger.debug("Thief " + this.id + " crawlOut (Position:" + this.position + ")");
        }

        //this.logger.debug("Thief " + this.id + " reached outside (Position:" + this.position + ")");
    }
    
    /**
     * Hand the canvas (if there is one) to the master thief. Waits inside handACanvas until the whole Party has returned.
     */
    private void handACanvas() throws Exception
    {
        this.setState(OrdinaryThief.OUTSIDE);
        
        //this.logger.debug("Thief " + this.id + " handACanvas (HasCanvas:" + this.hasCanvas + ")");
        //this.logger.log(this);
        
        this.clock.increment();
        this.controlCollection.handACanvas(this);
        this.logger.log(this);
        
        this.leaveParty();
    }
    
    /**
     * The writeObject method is called on serialization and is used to override the default java serialization.
     * @param out ObjectOutputStream used on serialization.
     * @throws IOException Exception may be thrown. 
     */
    private void writeObject(ObjectOutputStream out) throws IOException
    {
        out.writeInt(this.id);
        out.writeInt(this.maximumDisplacement);
        out.writeInt(this.party);
        out.writeInt(this.state);
        out.writeInt(this.position);
        out.writeBoolean(this.hasCanvas);
        out.writeObject(this.clock);
    }
    
    /**
     * The writeObject method is called when rebuilding the object from serialized data.
     * @param in ObjectInputStream used on serialization.
     * @throws IOException Exception may be thrown. 
     * @throws ClassNotFoundException Exception may be thrown. 
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        this.id = in.readInt();
        this.maximumDisplacement = in.readInt();
        this.party = in.readInt();
        this.state = in.readInt();
        this.position = in.readInt();
        this.hasCanvas = in.readBoolean();
        this.clock = (VectorialClock) in.readObject();
    }
    
    /**
     * Copy state from another thief instance.
     * @param thief Thief to copy data from.
     */
    public void copyState(OrdinaryThief thief)
    {
        this.id = thief.id;
        this.maximumDisplacement = thief.maximumDisplacement;
        this.party = thief.party;
        this.state = thief.state;
        this.position = thief.position;
        this.hasCanvas = thief.hasCanvas;
        this.clock = thief.clock;
    }
    
    /**
     * Compares two OrdinaryThieves.
     * @param object Object to be compared.
     * @return True if the thieves have the same id.
     */
    @Override
    public boolean equals(Object object)
    {
        if(object instanceof OrdinaryThief)
        {
            OrdinaryThief thief = (OrdinaryThief) object;
            
            return thief.getID() == this.id;
        }
        return false;
    }
    
    /**
     * Implements OrdinaryThief life cycle.
     */
    @Override
    public void run()
    {
        try
        {
            while(this.amINeeded())
            {
                this.prepareExecution();
                
                this.crawlIn();

                this.rollACanvas();
                this.reverseDirection();

                this.crawlOut();
                
                this.handACanvas();
            }
        }
        catch(Exception e)
        {
            System.out.println("Error: OrdinaryThief " + this.id);
            e.printStackTrace();
            System.exit(1);
        }
        
        System.out.println("Info: OrdinaryThief " + this.id + " terminated");
    }
}