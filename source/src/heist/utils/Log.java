package heist.utils;

/**
 * Auxiliary class to represent a log entry.
 * @author Jose Manuel
 */
public class Log implements Comparable
{
    /**
     * Log entry timestamp.
     */
    public int timestamp;
    
    /**
     * Log message.
     */
    public String message;

    /**
     * Log constructor from message and timestamp.
     * @param message Message.
     * @param timestamp Timestamp.
     */
    public Log(String message, int timestamp)
    {
        this.message = message;
        this.timestamp = timestamp;
    }
    
    /**
     * Compare log entry's using their timestamp values.
     * @param obj Message to compare.
     * @return 1 if message is more recent, -1 if message is older, 0 in case of error.
     */
    @Override
    public int compareTo(Object obj)
    {
        if(obj instanceof Log)
        {
            Log log = (Log) obj;
            
            if(this.timestamp > log.timestamp)
            {
                return 1;
            }
            else if (this.timestamp < log.timestamp)
            {
                return -1;
            }
        }
        
        return 0;
    }
}