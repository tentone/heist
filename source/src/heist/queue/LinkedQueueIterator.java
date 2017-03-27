package heist.queue;

/**
 * QueueIterator is used to iterate all elements inside a Queue.
 * @author Jose Manuel
 * @param <T> 
 */
class LinkedQueueIterator<T> implements Iterator
{
    private Node<T> node;
    
    /**
     * Constructor for the QueueIterator object.
     * @param node First node in the FIFO.
     */
    public LinkedQueueIterator(Node<T> node)
    {
        this.node = node;
    }

    /**
     * Check if there is some element left to iterate in the FIFO.
     * @return True if there is some element left, false otherwise.
     */
    @Override
    public boolean hasNext()
    {
        return this.node != null;
    }

    /**
     * Get the next element in the FIFO.
     * Returns null if there is no element available.
     * @return Element
     */
    @Override
    public T next()
    {
        if(this.hasNext())
        {
            T value = this.node.e;
            this.node = this.node.next;
            return value;
        }
        
        return null;
    }
}