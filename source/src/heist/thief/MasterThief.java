package heist.thief;

import heist.room.RoomStatus;
import heist.Configuration;
import heist.GeneralRepository;
import heist.shared.AssaultParty;
import heist.shared.ControlCollectionSite;
import heist.shared.ConcentrationSite;
import heist.log.Logger;

/**
 * MasterThief is an active entity responsible from planning and prepare the Heist.
 * It creates parties of Thieves and sends them to get Paintings from the Museum.
 * The MasterThief does not know how many paintings exist inside each Room but knows how many rooms there are inside the Museum and where they are.
 */
public class MasterThief extends Thread
{
    /**
     * Planning the heist state.
     * When the Master thief is in this state we is waiting for the OrdinaryThieves to be ready to start working.
     */
    public static final int PLANNING_THE_HEIST = 1000;
    
    /**
     * Deciding what to do state.
     * In this state the MasterThief is deciding what is his next state based on how many thieves are available to create parties, how many rooms have been targeted and how many rooms still have paintings in them.
     */
    public static final int DECIDING_WHAT_TO_DO = 2000;
    
    /**
     * Assembling a party state.
     */
    public static final int ASSEMBLING_A_GROUP = 3000;
    
    /**
     * Waiting for group arrival state.
     * In this state the MasterThief waits for the OrdinaryThieves attacking the museum.
     */
    public static final int WAITING_FOR_GROUP_ARRIVAL = 4000;
    
    /**
     * Presenting report state.
     * In this state the MasterThief signals everybody that the heist is over.
     */
    public static final int PRESENTING_THE_REPORT = 5000;

    /**
     * Control and Collection Site
     */
    private final ControlCollectionSite collection;
   
    /**
     * Concentration Site
     */
    private final ConcentrationSite concentration;
    
    /**
     * Logger
     */
    private final Logger logger;
    
    /**
     * Simulation configuration.
     */
    private final Configuration configuration;
    
    /**
     * Thief state.
     */
    private int state;

    /**
     * MasterThief constructor
     * @param repository GeneralRepository
     * @param configuration Simulation configuration
     */
    public MasterThief(GeneralRepository repository, Configuration configuration)
    {
        this.state = MasterThief.PLANNING_THE_HEIST;
        
        this.collection = repository.getCollectionSite();
        this.concentration = repository.getConcentrationSite();
        this.logger = repository.getLogger();
        
        this.configuration = configuration;
    }
    
    /**
     * Change MasterThief state
     * @param state 
     */
    private void setState(int state)
    {
        this.state = state;

        this.logger.debug("Master change state " + this.state);
    }
    
    /**
     * Get MasterThief state
     * @return Thief state.
     */
    public int state()
    {
        return this.state;
    }
    
    /**
     * This is the first state change in the MasterThief life cycle it changes the MasterThief state to deciding what to do. 
     */
    private void startOperations()
    {
        this.setState(MasterThief.DECIDING_WHAT_TO_DO);
        
        this.logger.log();
    }
    
    /**
     * Analyse the situation and take a decision.
     * Decision can be to create a new assault party, take a rest or to sum up results and end the heist.
     * @throws java.lang.InterruptedException Exception
     */
    private void appraiseSit() throws InterruptedException
    {
        this.setState(this.collection.appraiseSit());
        
        this.logger.debug("Master appraiseSit");
        this.logger.log();
    }
    
    /**
     * Assembly an assault party with thieves from the ConcentrationSite.
     * @return AssaultParty assembled.
     * @throws java.lang.InterruptedException Exception
     */
    private AssaultParty prepareAssaultParty() throws InterruptedException
    {
        RoomStatus room = this.collection.getRoomToAttack(this.configuration.partySize);
        AssaultParty party = this.concentration.createNewParty(this.configuration.partySize, this.configuration.thiefDistance, room);
        
        this.logger.debug("Master prepareAssaultParty (ID:" + party.getID() + " TargetID:" + party.getTarget() + " TargetDistance:" + party.getTargetDistance() + " TargetTA" + room.getThievesAttacking() + " Members:" + party.toString() + ")");
        this.logger.log();
        
        return party;
    }
    
    /**
     * Send assault party with thieves from the party created.
     * Wakes up the first thief in the party. That thief will wake the other thieves.
     * @param party Party to send.
     * @throws java.lang.InterruptedException Exception
     */
    private void sendAssaultParty(AssaultParty party) throws InterruptedException
    {
        party.sendParty();
        this.setState(MasterThief.DECIDING_WHAT_TO_DO);
        
        this.logger.debug("Master sendAssaultParty " + party.getID());
        this.logger.log();
    }
    
    /**
     * The MasterThief waits in the CollectionSite until is awaken by an incoming OrdinaryThief.
     * @throws java.lang.InterruptedException Exception
     */
    private void takeARest() throws InterruptedException
    {
        this.logger.debug("Master takeARest");
        
        this.collection.takeARest();
        
        this.logger.log();
    }
    
    /**
     * Collect canvas from thieve waiting in the collection site.
     * Add canvas to the correspondent RoomStatus, if empty handed mark the room as clean.
     * @throws java.lang.InterruptedException Exception
     */
    private void collectCanvas() throws InterruptedException
    {
        this.collection.collectCanvas();
        this.setState(MasterThief.DECIDING_WHAT_TO_DO);
        
        this.logger.debug("Master collectCanvas (Total:" + this.collection.totalPaintingsStolen() + ")");
        this.logger.log();
        
    }
    
    /**
     * Sum up the heist results, prepare a log of the heist and end the hole simulation.
     * Stop all thieves.
     */
    private void sumUpResults()
    {
        this.collection.sumUpResults();
        
        this.logger.debug(this.collection.totalPaintingsStolen() + " paintings were stolen!");
        this.logger.log();
        this.logger.end();
    }
    
    /**
     * Implements MasterThief life cycle.
     */
    @Override
    public void run()
    {
        this.logger.debug("Master started");
        
        try
        {
            this.startOperations();

            while(this.state != MasterThief.PRESENTING_THE_REPORT)
            {
                this.appraiseSit();

                if(this.state == MasterThief.WAITING_FOR_GROUP_ARRIVAL)
                {
                    this.logger.debug("Master WAITING_FOR_GROUP_ARRIVAL");
                    this.takeARest();
                    this.collectCanvas();
                }
                else if(this.state == MasterThief.ASSEMBLING_A_GROUP)
                {
                    this.logger.debug("Master ASSEMBLING_A_GROUP");
                    this.sendAssaultParty(this.prepareAssaultParty());
                }
            }
            
            this.logger.debug("Master PRESENTING_THE_REPORT");
            this.sumUpResults();
        }
        catch(InterruptedException e)
        {
            this.logger.debug("Master error");
            e.printStackTrace();
        }

        
        this.logger.debug("Master terminated");
    }
}
