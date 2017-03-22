package heist;

import heist.utils.Range;

/**
 * This class is used to store the simulation configuration.
 * Can be used to store and prepare tests for different configurations.
 */
public class Configuration
{
    public final int numberThieves, numberRooms, partySize;
    public final Range thiefDisplacement, roomDistance, numberPaintings;
    
    /**
     * Default constructor for Configuration with predefined values
     */
    public Configuration()
    {
        this.numberThieves = 6;
        this.thiefDisplacement = new Range(2, 6);
        this.numberRooms = 5;
        this.roomDistance = new Range(15, 30);
        this.numberPaintings = new Range(8, 16);
        this.partySize = 3;
    }
}
