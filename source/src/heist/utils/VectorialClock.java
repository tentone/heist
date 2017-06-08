package heist.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    public synchronized int getTime()
    {
        return time;
    }
    
    /**
     * Increment time counter by one.
     */
    public synchronized void increment()
    {
        time++;
    }
    
    /**
     * Set new time value to the clock.
     * @param time New time for the clock
     */
    public synchronized void synchronize(int time)
    {
        this.time = time;
    }
    
    /**
     * The writeObject method is called on serialization and is used to override the default java serialization.
     * @param out ObjectOutputStream used on serialization.
     * @throws IOException Exception may be thrown. 
     */
    private void writeObject(ObjectOutputStream out) throws IOException
    {
        this.time++;
        
        out.writeInt(this.time);
    }
    
    /**
     * The writeObject method is called when rebuilding the object from serialized data.
     * @param in ObjectInputStream used on serialization.
     * @throws IOException Exception may be thrown. 
     * @throws ClassNotFoundException Exception may be thrown. 
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        this.time = in.readInt();
    }
    
}
