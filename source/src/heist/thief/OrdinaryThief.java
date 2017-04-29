package heist.thief;

import heist.concurrent.shared.SharedAssaultParty;
import heist.Configuration;
import heist.interfaces.AssaultParty;
import heist.interfaces.ConcentrationSite;
import heist.interfaces.ControlCollectionSite;
import heist.interfaces.Logger;
import heist.interfaces.Museum;

/**
 * OrdinaryThief represents a thief active entity.
 * Thieves enter the museum stole canvas and come back to return it to the MasterThief.
 * Thieves attack in parties defined by the MasterThief and cannot move their comrades behind.
 * After taking a step the thief wakes up the last thief in the Party queue to make a step.
 */
public class OrdinaryThief extends Thread
{
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
     * Current AssaultParty attributed to this thief.
     * Null if the thief is not in an AssaultParty.
     */
    private AssaultParty party;
    
    /**
     * Thief unique id.
     */
    private final int id;
    
    /**
     * How far can the thief move in one step.
     */
    private final int maximumDisplacement;
    
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
     * OrdinaryThief constructor.
     * @param id Thief id.
     * @param controlCollection ControlCollectionSite
     * @param concentration ConcentrationSite
     * @param museum Museum
     * @param logger Logger
     * @param configuration Simulation configuration
     */
    public OrdinaryThief(int id, ControlCollectionSite controlCollection, ConcentrationSite concentration, Museum museum, Logger logger, Configuration configuration)
    {
        this.id = id;
        this.state = OrdinaryThief.OUTSIDE;
        
        this.controlCollection = controlCollection;
        this.concentration = concentration;
        this.museum = museum;
        this.logger = logger;
        
        this.maximumDisplacement = configuration.thiefDisplacement.generateInRange();
        
        this.position = 0;
        this.hasCanvas = false;
        this.party = null;
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
        if(this.party == null)
        {
            return 'W';
        }
        
        return 'P';
    }
    
    /**
     * Get the OrdinaryThief party
     * @return Party assigned to the OrdinaryThief.
     */
    public AssaultParty getParty()
    {
        return this.party;
    }
    
    /**
     * Set assault party.
     * @param party Assault party.
     */
    public void setParty(SharedAssaultParty party)
    {
        this.party = party;
    }
    
    /**
     * Leave party is called after handing the canvas to the MasterThief.
     */
    public void leaveParty() throws Exception
    {
        if(this.party != null)
        {
            this.party.removeThief(this.id);
            this.party = null;
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
        //this.logger.debug("Thief " + this.id + " amINeeded");
        this.logger.log();
        
        return this.controlCollection.amINeeded(this);
    }
    
    /**
     * Prepare execution, assign party to thief and change state to crawling inwards and sets thief to sleep until the master thief or another thief wakes it up.
     * Enter the concentration site and wait until a party is assigned.
     * @throws java.lang.Exception Exception     
     */
    private void prepareExecution() throws Exception
    {
        //this.logger.debug("Thief " + this.id + " entered the concentration site");
        
        this.concentration.prepareExcursion(this);
        
        //this.logger.debug("Thief " + this.id + " party assigned " + this.party.getID());
        this.logger.log();
    }
    
    /**
     * Updates thief position inside the museum and set thief back to sleep, until another thief wakes it up.
     * @throws java.lang.InterruptedException Exception
     */
    private void crawlIn() throws Exception
    {
        this.setState(OrdinaryThief.CRAWLING_INWARDS);
        this.logger.log();
        
        while(this.party.crawlIn(this))
        {
            //this.logger.debug("Thief " + this.id + " crawlIn (Position:" + this.position + ")");
            this.logger.log();
        }
        
        //this.logger.debug("Thief " + this.id + " reached room (Position:" + this.position + ")");
        this.logger.log();
    }
    
    /**
     * Try to collect a canvas from the room and reverse direction after.
     */
    private void rollACanvas() throws Exception
    {
        this.setState(OrdinaryThief.AT_A_ROOM);
        this.hasCanvas = this.museum.rollACanvas(this.party.getTarget());

        //this.logger.debug("Thief " + this.id + " rollACanvas (HasCanvas:" + this.hasCanvas + ")");
        this.logger.log();
    }
    
    /**
     * Change state to crawling outwards.
     */
    private void reverseDirection() throws Exception
    {      
        this.party.reverseDirection();
        
        //this.logger.debug("Thief " + this.id + " reverse");
        this.logger.log();
    }
    
    /**
     * Update position crawling out of the museum.
     * @throws java.lang.InterruptedException Exception
     */
    private void crawlOut() throws Exception
    {
        this.setState(OrdinaryThief.CRAWLING_OUTWARDS);
        this.logger.log();
        
        while(this.party.crawlOut(this))
        {
            //this.logger.debug("Thief " + this.id + " crawlOut (Position:" + this.position + ")");
            this.logger.log();
        }
        
        //this.logger.debug("Thief " + this.id + " reached outside (Position:" + this.position + ")");
        this.logger.log();
    }
    
    /**
     * Hand the canvas (if there is one) to the master thief. Waits inside handACanvas until the whole Party has returned.
     */
    private void handACanvas() throws Exception
    {
        this.setState(OrdinaryThief.OUTSIDE);
        
        //this.logger.debug("Thief " + this.id + " handACanvas (HasCanvas:" + this.hasCanvas + ")");
        this.logger.log();
        
        this.controlCollection.handACanvas(this);
        this.leaveParty();
        
        this.logger.log();
    }

    /**
     * Implements OrdinaryThief life cycle.
     */
    @Override
    public void run()
    {
        //this.logger.debug("Thief " + this.id + " started (MD:" + this.maximumDisplacement + ")");
        
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
            //this.logger.debug("Thief " + this.id + " error");
            e.printStackTrace();
        }
        
        //this.logger.debug("Thief " + this.id + " terminated");
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
    
}