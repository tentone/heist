package heist.utils;

import java.io.Serializable;

/**
 * Vectorial clock is a one way counter to keep track of the message order.
 * The clock is serializable and should be updated every time a message is sent, and can be used as a timestamp to sort messages.
 * @author Jose Manuel
 */
public class VectorialClock implements Serializable
{
    private static final long serialVersionUID = 9882120011235175L; 
    
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
    
    /**
     * Set new time value to the clock.
     * @param time New time for the clock
     */
    public void synchronize(int time)
    {
        this.time = time;
    }
}
