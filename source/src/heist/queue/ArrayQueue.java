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
    }
    
    @Override
    public void push(T e)
    {

    }

    @Override
    public T pop()
    {
        return null;
    }

    @Override
    public T peek()
    {
        return null;
    }

    @Override
    public boolean remove(T e)
    {
        return false;
    }

    @Override
    public void clear()
    {
        
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
        return false;
    }
}
