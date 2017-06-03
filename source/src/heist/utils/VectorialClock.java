package heist.utils;

/**
 * Vectorial clock is a one way counter to keep track of the message order.
 * @author Jose Manuel
 */
public class VectorialClock
{
    /**
     * Pseudo-time.
     */
    private int time;
    
    /**
     * Constructor sets time counter to zero. 
     */
    public VectorialClock()
    {
        this.time = 0;
    }
    
    /**
     * Get current time.
     * @return Current time.
     */
    public int getTime()
    {
        return time;
    }
    
    /**
     * Increment time counter by one.
     */
    public void increment()
    {
        time++;
    }
}
