package heist.queue;

/**
 * Class to represent a generic node used to create LikedLists.
 * @author Jose Manuel
 * @param <T> Generic type.
 */
public class Node<T> 
{
    /**
     * Element stored in the node.
     */
    public T e;
    
    /**
     * Pointer to next node.
     */
    public Node<T> next;
}