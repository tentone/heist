package heist.queue;

/**
 * Generic array based FIFO structure.
 * @author Jose Manuel
 * @param <T> Type of FIFO elements.
 */
public class ArrayQueue<T> implements Queue<T>
{
    /**
     * Max size for this ArrayQueue.
     * If the push method is called and the queue is already full a Exception is thrown.
     */
    private final int maxSize;
    
    /**
     * Current size of the queue.
     */
    private int size;
    
    
    /**
     * Fist and last queue elements pointer. 
     */
    private int first, last;
    
    /**
     * Array with data stored in the queue.
     */
    private T[] array;
    
    /**
     * ArrayQueue constructor.
     * @param maxSize ArrayQueue max size.
     */
    public ArrayQueue(int maxSize)
    {
        this.maxSize = maxSize;
        this.size = 0;
        
        this.first = -1;
        this.last = -1;
        
        this.array = (T[]) new Object[this.maxSize];
    }
    
    @Override
    public void push(T e)
    {
        //TODO <ADD CODE HERE>
    }

    @Override
    public T pop()
    {
        //TODO <ADD CODE HERE>
        return null;
    }

    @Override
    public T peek()
    {
        //TODO <ADD CODE HERE>
        return null;
    }

    @Override
    public boolean remove(T e)
    {
        //TODO <ADD CODE HERE>
        return false;
    }

    @Override
    public void clear()
    {
        //TODO <ADD CODE HERE>
    }

    @Override
    public int size()
    {
        return this.size;
    }

    @Override
    public boolean contains(T e)
    {
        //TODO <ADD CODE HERE>
        return false;
    }
}
