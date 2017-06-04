package heist;

import heist.utils.Range;
import java.io.Serializable;

/**
 * This class is used to store the simulation configuration.
 * Can be used to store and prepare tests for different configurations.
 * This configuration can be distributed to all elements in a distributed implementation.
 * @author Jose Manuel
 */
public class Configuration implements Serializable
{   
    private static final long serialVersionUID = 832654734123L;
    
    /**
     * Number of thieves to be created for this simulation.
     */
    public int numberThieves;
    
    /**
     * Number of rooms inside the museum.
     * Number of rooms has to be less or equal to the possible values for room distance.
     */
    public int numberRooms;
    
    /**
     * Party size.
     */
    public int partySize;
    
    /**
     * Number of parties in this simulation.
     */
    public int numberParties;
    
    /**
     * Max distance between crawling thieves.
     * Distance is only verified for the party.
     */
    public int thiefDistance;
            
    /**
     * Range of possible thief displacements.
     * Displacement defines how far the thief can move in one step.
     */
    public Range thiefDisplacement;
    
    /**
     * Range of room distances inside the museum.
     */
    public Range roomDistance;
    
    /**
     * Range of number painting inside each room.
     */
    public Range numberPaintings;
    
    /**
     * Flag to enable debug messages.
     */
    public boolean debug;
    
    /**
     * File path to log write log file.
     */
    public String logFile;
    
    /**
     * Flag to set if log is written to a file.
     */
    public boolean logToFile;
    
    /**
     * Print log messages as specified in the document.
     */
    public boolean  log;
    
    /**
     * If true the log header is printed every time.
    */
    public boolean logHeader;
    
    /**
     * If true the log is printed on receive, else the log is store and printed at the end sorted.
     */
    public boolean logImmediate;
    
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
        this.logToFile = true;
        this.debug = false;
        this.log = true;
        this.logHeader = false;
        this.logImmediate = false;
    }
}
