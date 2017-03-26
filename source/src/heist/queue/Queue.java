package heist.queue;

/**
 * Interface to represent FIFO structures.
 * @author Jose Manuel
 */
public interface Queue<T>
{
    public void push(T e);
    public T pop();
    public T peek();
    public boolean remove(T e);
    public void clear();
    public int size();
    public boolean isEmpty();
    public boolean contains(T e);
}
