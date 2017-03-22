package heist.struct;

import java.util.Iterator;

/**
 * Generic node based FIFO structure.
 * @author Jose Manuel
 * @param <T> Type of FIFO elements.
 */
@SuppressWarnings("unchecked")
public class Queue<T> 
{
    private Node<T> first = null, last = null;
    private int size = 0;
    
    /**
     * Add a new element to the FIFO.
     * @param e Element
     */
    public void push(T e)
    {
        Node<T> node = new Node<T>();
        node.e = e;
        node.next = null;

        if(isEmpty())
        {
            first = node;
        }
        else
        {
            last.next = node;
        }
        
        last = node;
        size++;
    }
    
    /**
     * Remove the first element from the FIFO and return it.
     * @return Element removed from the FIFO.
     */
    public T pop() 
    {
        T elem = first.e;
        
        size--;
        first = first.next;
        if(first == null)
        {
            last = null;
        }
        
        return elem;
    }

    /**
     * Check the first element on the FIFO without removing it.
     * @return The first element on the FIFO.
     */
    public T peek() 
    {
        return first.e;
    }
    
    /**
     * Check the size
     * @return Size
     */
    public int size() 
    {
        return size;
    }

    /**
     * Check if the FIFO is empty.
     * @return True if the FIFO is empty.
     */
    public boolean isEmpty() 
    {
        return size() == 0;
    }
    
    /**
     * Return QueueIterator object to iterate all elements inside the FIFO.
     * @return QueueIterator object
     */
    public Iterator<T> iterator()
    {
        return new QueueIterator<T>(first);
    }
}