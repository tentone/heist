package heist.queue;

/**
 * FIFO structure with a defined max size that automatically discards old elements when the maximum size is reached.
 * @author Jose Manuel
 * @param <T> Type of FIFO elements.
 */
public class LeakingQueue<T> extends ArrayQueue<T>
{
    /**
     * LeakingQueue constructor.
     * @param maxSize Queue max size.
     */
    public LeakingQueue(int maxSize)
    {
        super(maxSize);
    }
    
    /**
     * Add new element to the Queue top.
     * If the queue is full the oldest element is removed to free space for the new element.
     * @param e 
     */
    @Override
    public void push(T e)
    {
        if(this.size == this.maxSize)
        {
            this.pop();
        }
        
        this.last++;
        this.array[this.last] = e;
        this.size++;
    }
}
