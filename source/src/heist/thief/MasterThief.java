package heist.thief;

import heist.RoomStatus;
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
    public static final int PLANNING_THE_HEIST = 1000;
    public static final int DECIDING_WHAT_TO_DO = 2000;
    public static final int ASSEMBLING_A_GROUP = 3000;
    public static final int WAITING_FOR_GROUP_ARRIVAL = 4000;
    public static final int PRESENTING_THE_REPORT = 5000;

    private final ControlCollectionSite collection;
    private final ConcentrationSite concentration;
    private final Logger logger;
    
    private final Configuration configuration;
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
     * This is the first state change in the MasterThief life cycle it changes the MasterThief state to deciding what to do. 
     */
    private void startOperations()
    {
        this.setState(MasterThief.DECIDING_WHAT_TO_DO);
    }
    
    /**
     * Analyse the situation and take a decision.
     * Decision can be to create a new assault party, take a rest or to sum up results and end the heist.
     * @throws java.lang.InterruptedException Exception
     */
    private void appraiseSit() throws InterruptedException
    {
        this.logger.debug("Master appraiseSit");
        
        this.setState(this.collection.appraiseSit());
    }
    
    /**
     * Assembly an assault party with thieves from the ConcentrationSite.
     * @return AssaultParty assembled.
     * @throws java.lang.InterruptedException Exception
     */
    private AssaultParty prepareAssaultParty() throws InterruptedException
    {
        RoomStatus room = this.collection.nextTargetRoom();
        room.addThievesAttacking(this.configuration.partySize);

        this.logger.debug("Master prepareAssaultParty room [ID: " + room.getID() + " P:" + room.getPaintings() + " TA:" + room.getThievesAttacking() + "]"); 
        
        AssaultParty party = this.concentration.createNewParty(this.configuration.partySize, this.configuration.thiefDistance, room);
        
        this.logger.debug("Master prepareAssaultParty (ID:" + party.getID() + " TargetID:" + party.getTarget() + " TargetDistance:" + party.getTargetDistance() + " Members:" + party.toString() + ")");
        
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
        this.logger.debug("Master sendAssaultParty " + party.getID());
        
        party.sendParty();
        this.setState(MasterThief.DECIDING_WHAT_TO_DO);
    }
    
    /**
     * The MasterThief waits in the CollectionSite until is awaken by an incoming OrdinaryThief.
     * @throws java.lang.InterruptedException Exception
     */
    private void takeARest() throws InterruptedException
    {
        this.logger.debug("Master takeARest");
        
        this.collection.takeARest();
    }
    
    /**
     * Collect canvas from thieve waiting in the collection site.
     * Add canvas to the correspondent RoomStatus, if empty handed mark the room as clean.
     * @throws java.lang.InterruptedException Exception
     */
    private void collectCanvas() throws InterruptedException
    {
        this.collection.collectCanvas();

        this.logger.debug("Master collectCanvas (Total:" + this.collection.totalPaintingsStolen() + ")");
        
        this.setState(MasterThief.DECIDING_WHAT_TO_DO);
    }
    
    /**
     * Sum up the heist results, prepare a log of the heist and end the hole simulation.
     * Stop all thieves.
     */
    private void sumUpResults()
    {
        this.collection.sumUpResults();
        
        this.logger.debug(this.collection.totalPaintingsStolen() + " paintings were stolen!!!");
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
