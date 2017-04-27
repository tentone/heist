package heist;

import heist.utils.Range;

/**
 * This class is used to store the simulation configuration.
 * Can be used to store and prepare tests for different configurations.
 * This configuration can be distributed to all elements in a distributed implementation.
 * @author Jose Manuel
 */
public class Configuration
{   
    /**
     * Number of thieves to be created for this simulation.
     */
    public final int numberThieves;
    
    /**
     * Number of rooms inside the museum.
     * Number of rooms has to be less or equal to the possible values for room distance.
     */
    public final int numberRooms;
    
    /**
     * Party size.
     */
    public final int partySize;
    
    /**
     * Number of parties in this simulation.
     */
    public final int numberParties;
    
    /**
     * Max distance between crawling thieves.
     * Distance is only verified for the party.
     */
    public final int thiefDistance;
            
    /**
     * Range of possible thief displacements.
     * Displacement defines how far the thief can move in one step.
     */
    public final Range thiefDisplacement;
    
    /**
     * Range of room distances inside the museum.
     */
    public final Range roomDistance;
    
    /**
     * Range of number painting inside each room.
     */
    public final Range numberPaintings;
    
    /**
     * Flag to enable debug messages.
     */
    public final boolean debug;
    
    /**
     * File path to log write log file.
     */
    public final String logFile;
    
    /**
     * Flag to set if log is written to a file.
     */
    public final boolean logToFile;
    
    /**
     * Print log messages as specified in the document.
     */
    public final boolean  log;
    
    /**
     * If true the log header is printed every time.
    */
    public final boolean logHeader;
    
    /**
     * Default constructor for Configuration with default values.
     */
    public Configuration()
    {
        this.numberThieves = 6;
        this.thiefDistance = 3;
        this.thiefDisplacement = new Range(2, 6);
        
        this.partySize = 3;
        this.numberParties = this.numberThieves / this.partySize;
        
        this.numberRooms = 5;
        this.roomDistance = new Range(15, 30);
        this.numberPaintings = new Range(8, 16);
        
        this.logFile = "log.txt";
        this.logToFile = false;
        this.debug = false;
        this.log = true;
        this.logHeader = true;
    }
}
