package heist.queue;

import heist.queue.iterator.ArrayQueueIterator;
import java.io.Serializable;

/**
 * Generic array based FIFO structure.
 * @author Jose Manuel
 * @param <T> Type of FIFO elements.
 */
@SuppressWarnings("unchecked")
public class ArrayQueue<T> implements Queue<T>, Serializable
{
    private static final long serialVersionUID = 3482001199233757399L;
    
    /**
     * Max size for this ArrayQueue.
     * If the push method is called and the queue is already full a Exception is thrown.
     */
    protected final int maxSize;
    
    /**
     * Current size of the queue.
     */
    protected int size;
    
    /**
     * Fist and last queue elements pointer. 
     */
    protected int last;
    
    /**
     * Array with data stored in the queue.
     */
    protected T[] array;
    
    /**
     * ArrayQueue constructor.
     * @param maxSize ArrayQueue max size.
     */
    public ArrayQueue(int maxSize)
    {
        this.maxSize = maxSize;
        this.size = 0;
        this.last = -1;
        
        this.array = (T[]) new Object[this.maxSize];
    }
    
    @Override
    public void push(T e)
    {
        this.last++;
        this.array[this.last] = e;
        this.size++;
    }

    @Override
    public T pop()
    {
        T e = this.array[0];
        
        for(int i = 0; i < this.last; i++)
        {
            this.array[i] = this.array[i + 1];
        }
        
        this.array[this.last] = null;
        this.last--;
        this.size--;
        
        return e;
    }

    @Override
    public T peek()
    {        
        return this.array[0];
    }

    @Override
    public boolean remove(T e)
    {
        for(int i = 0; i <= this.last; i++)
        {
            if(this.array[i] == e)
            {
                for(int j = i; j < this.last; j++)
                {
                    this.array[j] = this.array[j + 1];
                }
                
                this.array[this.last] = null;
                this.last--;
                this.size--;
                
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear()
    {
        for(int i = 0; i < this.array.length; i++)
        {
            this.array[i] = null;
        }
        
        this.last = -1;
        this.size = 0;
    }

    @Override
    public int size()
    {
        return this.size;
    }

    @Override
    public boolean contains(T e)
    {
        for(int i = 0; i < this.size; i++)
        {
            if(this.array[i].equals(e))
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public ArrayQueueIterator<T> iterator()
    {
        return new ArrayQueueIterator<>(this.array, this.size);
    }
 
    /**
     * Generate string with all elements inside the FIFO.
     * [Element1, Element2, Element3, ...]
     * @return String with elements inside the FIFO.
     */
    @Override
    public String toString()
    {
        String s = "[";
        
        for(int i = 0; i <= this.last; i++)
        {
            s += this.array[i].toString();
            
            if(i + 1 <= this.last)
            {
                s += ", ";
            }
        }
        
        return s + "]";
    }
}
