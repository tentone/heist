package heist.queue;

import heist.queue.iterator.Iterator;

/**
 * Interface to represent FIFO structures.
 * @author Jose Manuel
 * @param <T> Data type.
 */
public interface Queue<T>
{
    /**
     * Add a new element to the FIFO.
     * @param e Element to be added.
     */
    public void push(T e);
    
    /**
     * Remove the first element from the FIFO and returns it.
     * @return Element removed from the FIFO.
     */
    public T pop();
    
    /**
     * Get the first element on the FIFO without removing it.
     * @return The first element on the FIFO.
     */
    public T peek();
    
    /**
     * Removes a specific element from the FIFO.
     * @param e Element to be removed from the FIFO.
     * @return True if was able to remove the element, false otherwise.
     */
    public boolean remove(T e);
    
    /**
     * Clear the FIFO.
     * Removes all elements and set size to 0.
     */
    public void clear();
    
    /**
     * Get the size of the FIFO.
     * @return Size of the FIFO.
     */
    public int size();
    
    /**
     * Check if the FIFO contains an element.
     * Uses the == operator, the element has to be the exact same instance.
     * @param e Element to search for.
     * @return True if the FIFO contains the element.
     */
    public boolean contains(T e);
    
    /**
     * Get a Iterator object to iterate all elements inside the FIFO.
     * @return Iterator object.
     */
    public Iterator<T> iterator();
    
    /**
     * Get all elements in the FIFO as an array.
     * @return Array with all elements in the FIFO.
     */
    public T[] toArray();
    
    /**
     * Check if the FIFO is empty.
     * @return True if the FIFO is empty.
     */
    public default boolean isEmpty() 
    {
        return this.size() == 0;
    }
    
    /**
     * Move first element to the end of this FIFO.
     */
    public default void popPush()
    {
        this.push(this.pop());
    }
}
