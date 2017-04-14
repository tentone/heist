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

    @Override
    public T peek() 
    {
        if(this.first == null)
        {
            return null;
        }
        
        return this.first.e;
    }
    
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
    
    @Override
    public void clear()
    {
        this.first = null;
        this.last = null;
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

    @Override
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