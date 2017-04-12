package heist.queue.iterator;

/**
 * Iterator interface describes an object that can be iterated.
 * @author Jose Manuel
 */
public interface Iterator<T>
{
    /**
     * Check if there is some element left to iterate.
     * @return True if there is elements left.
     */
    public boolean hasNext();
    
    /**
     * Get next element available from the iterator.
     * Return null if there is no element available.
     * @return Next element from the iterator.
     */
    public T next();
}
