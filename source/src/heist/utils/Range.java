package heist.utils;

import java.util.Random;

/**
 * Range is used to represent a numeric integer range.
 */
public class Range
{
    private static Random random = new Random();
    public int min, max;
    
    /**
     * Range constructor from min and max values.
     * @param min Min value.
     * @param max Max value.
     */
    public Range(int min, int max)
    {
        this.min = min;
        this.max = max;
    }
    
    /**
     * Generate integer value between min (inclusive) and max (inclusive).
     * @return Value between min (inclusive) and max (inclusive).
     */
    public int generateInRange()
    {
        return min + random.nextInt(max - min + 1);
    }
}
