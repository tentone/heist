package heist.queue;

import java.util.Iterator;

/**
 * QueueIterator is used to iterate all elements inside a Queue object.
 * @author Jose Manuel
 * @param <T> 
 */
class QueueIterator<T> implements Iterator
{
    private Node<T> node;
    
    /**
     * Constructor for the QueueIterator object.
     * @param node First node in the FIFO.
     */
    public QueueIterator(Node<T> node)
    {
        this.node = node;
    }

    /**
     * Check if there is some element left to iterate.
     * @return True if there is some element left, False otherwise.
     */
    @Override
    public boolean hasNext()
    {
        return node != null;
    }

    /**
     * Get the next element.
     * @return Element
     */
    @Override
    public T next()
    {
       T value = node.e;
       node = node.next;
       return value; 
    }
}