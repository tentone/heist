package heist.utils;

import java.io.Serializable;

/**
 * Range is used to represent a numeric integer range.
 * @author Jose Manuel
 */
public class Range
{
    public int min, max;
    
    /**
     * Range constructor from min and max values
     * @param min Min value.
     * @param max Max value.
     */
    public Range(int min, int max)
    {
        this.min = min;
        this.max = max;
    }
}
