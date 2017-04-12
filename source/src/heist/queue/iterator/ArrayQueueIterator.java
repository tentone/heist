package heist.queue.iterator;

/**
 * QueueIterator is used to iterate all elements inside a Queue.
 * @author Jose Manuel
 * @param <T> 
 */
public class ArrayQueueIterator<T> implements Iterator
{
    /**
     * Array of element to iterate.
     */
    private T[] array;
    
    
    /**
     * How many elements to iterate in the array.
     */
    private int size;
    
    /**
     * Current element index.
     */
    private int index;
    
    /**
     * Constructor for the QueueIterator object.
     * ArrayQueue should pass the the elements array and the current queue size.
     * @param array Elements array.
     * @param size FIFO size.
     */
    public ArrayQueueIterator(T[] array, int size)
    {
        this.array = array;
        this.size = size;
        this.index = 0;
    }

    /**
     * Check if there is some element left to iterate in the FIFO.
     * @return True if there is some element left, false otherwise.
     */
    @Override
    public boolean hasNext()
    {
        return this.index < this.size;
    }

    /**
     * Get the next element in the FIFO.
     * Returns null if there is no element available.
     * @return Element
     */
    @Override
    public T next()
    {
        if(this.hasNext())
        {
            T value = this.array[this.index];
            this.index++;
            return value;
        }
        
        return null;
    }
}