package heist.queue;

/**
 * Generic array based FIFO structure.
 * @author Jose Manuel
 * @param <T> Type of FIFO elements.
 */
public class ArrayQueue<T> implements Queue<T>
{
    private final int maxSize;
    private int size;
    
    public ArrayQueue(int maxSize)
    {
        this.maxSize = maxSize;
        this.size = 0;
        
        //TODO <ADD CODE HERE>
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
    public boolean isEmpty()
    {
        return this.size() == 0;
    }

    @Override
    public boolean contains(T e)
    {
        //TODO <ADD CODE HERE>
        return false;
    }
}
