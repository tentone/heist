package heist;

import heist.utils.Range;

/**
 * This class is used to store the simulation configuration.
 * Can be used to store and prepare tests for different configurations.
 * This configuration can be distributed to all elements in a distributed implementation.
 */
public class Configuration
{
    public final int numberThieves, numberRooms, partySize, thiefDistance;
    public final Range thiefDisplacement, roomDistance, numberPaintings;
    
    public final boolean debug;
    
    public final String logFile;
    public final boolean logToFile, log, logHeader;
    
    /**
     * Default constructor for Configuration with default values.
     */
    public Configuration()
    {
        this.numberThieves = 6;
        this.thiefDistance = 3;
        this.thiefDisplacement = new Range(2, 6);
        
        this.partySize = 3;
        
        this.numberRooms = 5;
        this.roomDistance = new Range(15, 30);
        this.numberPaintings = new Range(8, 16);
        
        this.logFile = "log.txt";
        this.logToFile = false;
        this.debug = true;
        this.log = true;
        this.logHeader = true;
    }
}
