package heist.queue;

/**
 * Interface to represent FIFO structures.
 * @author Jose Manuel
 * @param <T> Data type.
 */
public interface Queue<T>
{
    /**
     * Add elements to the FIFO.
     * @param e Element to Add.
     */
    public void push(T e);
    
    /**
     * Remove element from the FIFO.
     * @return Element removed.
     */
    public T pop();
    
    /**
     * See first element of the FIFO.
     * @return First element.
     */
    public T peek();
    
    /**
     * Remove an element from the FIFO.
     * @param e Element to Remove.
     * @return True if element was removed.
     */
    public boolean remove(T e);
    
    /**
     * Clear the FIFO.
     */
    public void clear();
    
    /**
     * Get the size of the FIFO.
     * @return Size of the FIFO.
     */
    public int size();
    
    /**
     * Check if the FIFO contains an element.
     * @param e Element to search for.
     * @return True if the FIFO contains the element.
     */
    public boolean contains(T e);
    
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
