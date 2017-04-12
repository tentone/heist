package heist.queue;

import heist.queue.iterator.Iterator;
import heist.queue.iterator.LinkedQueueIterator;

/**
 * Generic node based FIFO structure.
 * @author Jose Manuel
 * @param <T> Type of FIFO elements.
 */
@SuppressWarnings("unchecked")
public class LinkedQueue<T> implements Queue<T>
{
    /**
     * First and last node of the FIFO.
     */
    private Node<T> first, last;
    
    /**
     * Current size of the FIFO.
     */
    private int size;
    
    /**
     * LinkedQueue constructor, initializes first and last nodes and sets the size.
     */
    public LinkedQueue()
    {
        this.first = null;
        this.last = null;
        this.size = 0;
    }
        
    /**
     * Add a new element to the FIFO.
     * @param e Element
     */
    @Override
    public void push(T e)
    {
        Node<T> node = new Node<>();
        node.e = e;
        node.next = null;

        if(this.isEmpty())
        {
            this.first = node;
        }
        else
        {
            this.last.next = node;
        }
        
        this.last = node;
        this.size++;
    }
    
    /**
     * Remove the first element from the FIFO and return it.
     * @return Element removed from the FIFO.
     */
    @Override
    public T pop() 
    {
        T elem = this.first.e;
        
        this.size--;
        this.first = this.first.next;
        if(this.first == null)
        {
            this.last = null;
        }
        
        return elem;
    }

    /**
     * Check the first element on the FIFO without removing it.
     * @return The first element on the FIFO.
     */
    @Override
    public T peek() 
    {
        if(this.first == null)
        {
            return null;
        }
        
        return this.first.e;
    }
    
    /**
     * Remove specific element from the FIFO.
     * @param e Element to be removed from the FIFO.
     * @return True if was able to remove the element, false otherwise.
     */
    @Override
    public boolean remove(T e)
    {
        Node temp = this.first;

        if(temp.e == e)
        {
            this.first = this.first.next;
            this.size--;
            return true;
        }
            
        while(temp.next != null)
        {
            if(temp.next == this.last)
            {
                if(this.last.e == e)
                {
                    this.last = temp;
                    this.last.next = null;
                    this.size--;
                    return true;
                }
            }
            
            if(temp.next.e == e)
            {
                temp.next = temp.next.next;
                this.size--;
                return true;
            }
            
            temp = temp.next;
        }
        
        return false;
    }
    
    /**
     * Clear the FIFO. Remove all elements and set size to 0.
     */
    @Override
    public void clear()
    {
        this.first = null;
        this.last = null;
        this.size = 0;
    }
    
    /**
     * Check the size
     * @return Size
     */
    @Override
    public int size() 
    {
        return this.size;
    }

    /**
     * Check if a element exists inside the FIFO.
     * Uses the == operator, the element has to be the exact same instance.
     * @param e Element to look for.
     * @return True if element was found inside the queue.
     */
    @Override
    public boolean contains(T e)
    {
        Iterator<T> it = this.iterator();
        while(it.hasNext())
        {
            if(it.next() == e)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Return QueueIterator object to iterate all elements inside the FIFO.
     * Be careful when using inside shared objects since changes to the nodes can lock the iterator. 
     * @return QueueIterator object
     */
    public Iterator<T> iterator()
    {
        return new LinkedQueueIterator<>(first);
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
        Iterator<T> it = this.iterator();
        while(it.hasNext())
        {
            s += it.next().toString();
            if(it.hasNext())
            {
                s += ", ";
            }
        }
        return s + "]";
    }
}