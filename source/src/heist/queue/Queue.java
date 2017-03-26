package heist.queue;

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
     * Move first element to the end of this FIFO.
     */
    public void popPush()
    {
        this.push(this.pop());
    }
    
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
     * Remove specific element from the FIFO.
     * @param e Element to be removed from the FIFO.
     * @return True if was able to remove the element, false otherwise.
     */
    public boolean remove(T e)
    {
        Node temp = first;

        if(temp.e == e)
        {
            first = first.next;
            return true;
        }
            
        while(temp.next != null)
        {
            if(temp.next.e == e)
            {
                temp.next = temp.next.next;
                return true;
            }
            
            temp = temp.next;
        }
                
        return false;
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
    
    /**
     * Return QueueIterator object to iterate all elements inside the FIFO.
     * Be careful when using inside shared objects since changes to the nodes can lock the iterator. 
     * @return QueueIterator object
     */
    public Iterator<T> iterator()
    {
        return new QueueIterator<T>(first);
    }
}