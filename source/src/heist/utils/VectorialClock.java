package heist.utils;

import java.io.Serializable;

/**
 * Vectorial clock is a one way counter to keep track of the message order.
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
}
